ThisBuild / organization := "com.okjike.data"
ThisBuild / version := "1.0.2-SNAPSHOT"
ThisBuild / scalaVersion := "2.11.12"

lazy val sparkVersion = "2.4.0"
lazy val breezeVersion = "0.13.2"
lazy val scalaCoreVersion        = "2.11.12"
lazy val scalaVersions           = Seq("2.11.12", "2.12.7")
lazy val mongodbDriverVersion    = "3.9.0"
lazy val slf4jVersion            = "1.7.16"
lazy val guavaVersion            = "12.0"
lazy val scalaTestVersion        = "3.0.5"
lazy val scalaCheckVersion       = "1.14.0"
lazy val scalaMockVersion        = "3.6.0"
lazy val junitVersion            = "4.12"
lazy val junitInterfaceVersion   = "0.11"

// Libraries
lazy val mongodbDriver     = "org.mongodb" % "mongo-java-driver" % mongodbDriverVersion
lazy val sparkCore         = "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
lazy val sparkSql          = "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
lazy val slf4j             = "org.slf4j" % "slf4j-api" % slf4jVersion % "provided"
lazy val guava             = "com.google.guava" % "guava" % guavaVersion % "provided"

// Test
lazy val scalaTest         = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
lazy val scalaCheck        = "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
lazy val scalaMock         = "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % "test"
lazy val junit             = "junit" % "junit" % junitVersion % "test"
lazy val junitInterface    = "com.novocode" % "junit-interface" % junitInterfaceVersion % "test"
lazy val sparkStreaming    = "org.apache.spark" %% "spark-streaming" % sparkVersion % "test"

// Projects
lazy val coreDependencies     = Seq(mongodbDriver, sparkCore, sparkSql, slf4j)
lazy val testDependencies     = Seq(scalaTest, scalaCheck, scalaMock, junit, junitInterface, sparkStreaming)

import com.amazonaws.auth.{AWSCredentialsProviderChain, DefaultAWSCredentialsProviderChain}
import com.amazonaws.auth.profile.ProfileCredentialsProvider


lazy val root = (project in file("."))
  .settings(
    name := "jike-mongo-spark",
    libraryDependencies ++= coreDependencies,
    fork in Test := true,
    parallelExecution in Test := false,
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled"),
    publishTo in ThisBuild := {
      val nexus = "s3://s3-cn-north-1.amazonaws.com/jike-aws-maven/repositories/"
      if (isSnapshot.value) {
        Some("aws-snapshots" at nexus + "snapshots")
      } else {
        Some("aws-releases"  at nexus + "releases")
      }
    },
    s3CredentialsProvider := { bucket: String =>
      new AWSCredentialsProviderChain(
        // This will first check system environment $AWS_CREDENTIAL_PROFILES_FILE,
        // If not set, use default location (~/.aws/credentials).
        // If you want to publish on your local machine, please push your AWS credential file to "~/.aws/credentials"
        new ProfileCredentialsProvider(),
        DefaultAWSCredentialsProviderChain.getInstance()
      )
    },
    scalacOptions in (Compile, doc) ++= Seq("-groups")
  )