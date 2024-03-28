pipeline {
    agent {
        label 'agent1'
    }

    tools {
        maven 'Maven 3.9.6'
        jdk 'jdk17'
        jfrog 'jfrog-cli'
    }

    environment {
        ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le dernier commit
                script {
                    git 'checkout origin/develop'
                    sh 'git pull'
                }
            }
        }
        stage('Build and Test') {
            steps {
                sh 'pwd'
                sh 'git log -n 1'
                // Vos étapes de construction et de test ici
            }
        }
        stage('Upload W4E jar to JFrog Artifactory') {
            steps {
                dir("backend/target") {
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER
                }
            }
        }
    }
}
