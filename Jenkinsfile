node {
  checkout scm

  stage('Build') {
    configFileProvider([configFile(fileId: '6544f843-17ba-4650-b029-810765923daa', variable: 'MY_SETTINGS')]) {
      docker.withRegistry('https://804775010343.dkr.ecr.cn-north-1.amazonaws.com.cn') {
        docker.image('maven-jenkins:jdk-8-slim').inside("-v /home/jenkins/.m2/:/home/jenkins/.m2") {
          if (env.BRANCH_NAME == 'master') {
            sh 'sbt clean'
            sh 'sbt publish'
          }
          sh 'echo congrats'
        }
      }
    }
  }
}