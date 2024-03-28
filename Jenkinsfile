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
        stage('main') {
            when{
               branch 'main'
            }
            steps {
                sh 'sudo ./build-all.sh'
                sh 'sudo docker compose down && sudo docker compose up -d'
                sh 'cd backend && sudo mvn verify'

                dir("backend/target") {
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER

                }
                dir("cli/target"){
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app'}
            }
        }
        stage('develop') {
                    when{
                       branch 'develop'
                    }
                    steps {
                        sh 'sudo docker compose down && sudo docker compose up -d'
                        sh 'cd backend && sudo mvn package verify'
                        sh 'cd cli && sudo mvn package'

                        dir("backend/target") {
                            sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER

                        }
                        dir("cli/target"){
                            sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app'}
                    }
                }

        stage("other"){
            when{
                branch 'feature*'
            }
            steps{
                sh 'cd backend && sudo mvn clean package'
                sh 'cd cli && sudo mvn clean install package'
            }

        }
    }
}
