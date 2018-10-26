node {
    if (env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'dev') {
        stage('Clone Repository') {
            checkout scm
        }

        stage('Maven Build') {
            docker.image('maven:3-alpine').inside('-v /root/.m2:/root/.m2') {
                sh 'mvn -B -DskipTests clean install'
            }

            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
    }
}