node {
  checkout scm
  env.AWS_DEFAULT_REGION = 'cn-north-1'
  env.AWS_ACCESS_KEY_ID = 'AKIAOXRSLLCIFGZYWNIA'
  env.AWS_SECRET_ACCESS_KEY = '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'
  stage('Build') {
    docker.withRegistry('https://804775010343.dkr.ecr.cn-north-1.amazonaws.com.cn', 'ecr:cn-north-1:9f34c613-7c6d-4132-baf8-a14d8689ed4a') {
      docker.image('sbt-aws:0.0.1').inside("-v /home/jenkins/.ivy2/:/home/jenkins/.ivy2") {
        if (env.BRANCH_NAME == 'master') {
          sh 'sbt clean'
          sh 'sbt publish'
        }
        sh 'echo congrats'
      }
    }
  }
}