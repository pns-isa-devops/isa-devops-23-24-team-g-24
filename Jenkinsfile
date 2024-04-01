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
                sh 'cd cli && sudo mvn clean package'
                dir("backend/target") {
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER

                }
                dir("cli/target"){
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app'
                }



                 sh 'sudo docker tag team-g-tcf-scheduler-service:latest simonbeurel/team-g-tcf-scheduler-service:latest '
                 sh 'sudo docker tag team-g-tcf-bank-service:latest simonbeurel/team-g-tcf-bank-service:latest'


                 sh 'sudo docker push simonbeurel/team-g-tcf-scheduler-service:latest'
                 sh 'sudo docker push simonbeurel/team-g-tcf-bank-service:latest'

                sh 'sudo docker compose down'
            }
        }
        stage('develop') {
                    when{
                       branch 'develop'
                    }
                            steps {

                                sh 'sudo docker pull simonbeurel/team-g-tcf-scheduler-service:latest' //pull scheduler depuis docker hub
                                sh 'sudo docker pull simonbeurel/team-g-tcf-bank-service:latest' //pull bank depuis docker hub

                                sh 'cd backend && sudo ./build.sh' //build backend
                                sh 'cd cli && sudo ./build.sh' //build cli

                                sh 'sudo docker compose down && sudo docker compose up -d' //compose up

                                sh 'cd backend && sudo mvn clean verify' // test backend
                                sh 'cd cli && sudo mvn clean package' // test cli


                                dir("backend/target") {
                            sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app' //push backend

                        }
                        dir("cli/target"){
                            sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app' //push cli
                        }
                        sh 'sudo docker compose down'

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
            sh 'sudo docker compose down'



        }
    }
}
