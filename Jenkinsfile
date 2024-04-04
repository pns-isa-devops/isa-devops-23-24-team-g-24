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
            stage('Build') {
                when {
                    branch 'main'
                }
                steps {
                    script {
                        echo 'Executing Build stage...'
                        sh 'sudo ./build-all.sh'
                    }
                }
            }

            stage('Docker') {
                when {
                    branch 'main'
                }
                steps {
                    script {
                        echo 'Executing Docker stage...'
                        sh 'sudo docker compose down && sudo docker compose up -d'
                    }
                }
            }

            stage('Test') {
                when {
                    branch 'main'
                }
                steps {
                    script {
                        echo 'Executing Test stage...'
                        sh 'cd backend && sudo mvn verify'
                        sh 'cd cli && sudo mvn clean package'
                        sh "sudo docker exec -i cli sh -c 'cat demo.txt' > cli/e2e_output.txt"
                        sh '''
                            if diff -q cli/e2e_output.txt cli/e2e_expected_output.txt; then
                                echo "Output matches expected output."
                            else
                                echo "Output does not match expected output."
                                exit 1
                            fi
                        '''
                    }
                }
            }
            stage('Artifactory') {
                when {
                    branch 'main'
                }
                steps {
                    script {
                        echo 'Executing Artifactory stage...'
                        dir("backend/target") {
                            sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} simpleTCFS-0.0.1-SNAPSHOT.jar /java_web-app'
                        }
                        dir("cli/target") {
                            sh 'jf rt upload --url http://vmpx07.polytech.unice.fr:8002/artifactory --access-token ${ARTIFACTORY_ACCESS_TOKEN} cli-0.0.1-SNAPSHOT.jar /java_web-app'
                        }
                    }
                }
            }
            stage('Docker Hub') {
                when {
                    branch 'main'
                }
                steps {
                    script {
                        echo 'Executing Docker Hub stage...'
                        sh 'sudo docker tag team-g-tcf-scheduler-service:latest simonbeurel/team-g-tcf-scheduler-service:latest '
                        sh 'sudo docker tag team-g-tcf-bank-service:latest simonbeurel/team-g-tcf-bank-service:latest'
                        sh 'sudo docker push simonbeurel/team-g-tcf-scheduler-service:latest'
                        sh 'sudo docker push simonbeurel/team-g-tcf-bank-service:latest'
                    }
                }
            }

            stage('Cleanup') {
                when {
                    branch 'main'
                }
                steps {
                    script {
                        echo 'Executing Cleanup stage...'
                        sh 'sudo docker compose down'
                    }
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
                sh 'sudo docker compose down'
            }


        }
    }
}
