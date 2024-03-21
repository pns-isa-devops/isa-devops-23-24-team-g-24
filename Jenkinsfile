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
    stage('Upload W4E jar to JFrog Artifactory') {
                steps {
                    dir("backend/target") {
                        sh 'jf rt upload --url http://artifactory:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} Winter4Everybody-0.0.1-SNAPSHOT.jar /libs-release-local' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER
                    }
                }
            }
}
