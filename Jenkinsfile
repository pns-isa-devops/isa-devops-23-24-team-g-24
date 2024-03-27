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
        stage('Build and Test') {
            steps {


                    sh 'pwd'
                    sh 'git log -n 1'
                    sh 'cd backend && sudo mvn clean package'
                    sh 'cd backend && sudo mvn test'

            }
        }
        stage('Upload W4E jar to JFrog Artifactory') {
            steps {
                dir("backend/target") {
                    sh 'jf rt upload --url http://172.17.0.2 --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /libs-release-local' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER
                }
            }
        }
    }

}
