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
                    sh 'pushd backend'
                    sh 'pwd'
                    sh 'git log -n 1'
                    sh 'sudo mvn clean package'
                    sh 'sudo mvn test'

            }
        }
    }

}
