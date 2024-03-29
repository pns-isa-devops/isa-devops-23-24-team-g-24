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
                //sh 'sudo docker compose up -d'
                sh 'cd backend && sudo mvn verify'
                dir("backend/target") {
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app' // HERE THE IP ADDRESS IS THE IP ADDRESS OF THE ARTIFACTORY DOCKER CONTAINER

                }
                dir("cli/target"){
                    sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app'
                }
                 sh 'docker tag team-g-tcf-scheduler-service:latest simonbeurel/team-g-tcf-scheduler-service:latest '
                 sh 'docker tag team-g-tcf-bank-service:latest simonbeurel/team-g-tcf-bank-service:latest'

                 sh 'docker push simonbeurel/team-g-tcf-scheduler-service:latest'
                 sh 'docker push simonbeurel/team-g-tcf-bank-service:latest'
            }
        }
        stage('develop') {
                    when{
                       branch 'develop'
                    }
                    steps {

                        sh 'cd bank && sudo ./build.sh'
                        sh 'cd scheduler && sudo ./build.sh'
                        sh 'cd backend && sudo mvn clean verify'
                        sh 'cd cli && sudo mvn clean install'

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
