node {
  checkout scm
  env.AWS_DEFAULT_REGION = 'cn-north-1'
  stage('Build') {
    docker.withRegistry('https://804775010343.dkr.ecr.cn-north-1.amazonaws.com.cn') {
      configFileProvider([
        configFile(fileId: '53c8501c-8511-4a2d-96c1-fc6708f23a1f', variable: 'AWS_SHARED_CREDENTIALS_FILE'),
        configFile(fileId: '53c8501c-8511-4a2d-96c1-fc6708f23a1f', variable: 'AWS_CREDENTIAL_PROFILES_FILE')
      ]) {
        docker.image('sbt-aws:0.0.1').inside("-v /home/jenkins/.ivy2/:/home/jenkins/.ivy2") {
          sh 'cat $AWS_SHARED_CREDENTIALS_FILE'
          sh 'cat $AWS_CREDENTIAL_PROFILES_FILE'
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
