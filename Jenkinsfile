pipeline {
    agent {
        label 'agent1'
    }

    environment {
        ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
    }

    stages {
        stage('Build and Test') {
            steps {


                    sh 'pwd'
                    sh 'git log -n 1'
                    sh 'cd backend && sudo mvn clean package'
                    sh 'cd backend && sudo mvn test'

            }
        }
    }

}
