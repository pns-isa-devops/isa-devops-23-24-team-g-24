pipeline {
    agent {
        label 'agent1'
    }
     tools {
            maven 'Maven 3.9.6'
            jdk 'jdk17'
        }
        environment {
            ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
        }

    stages {
        stage('Build and Test') {
            steps {
                dir('/home/jenkins/workspace/ch_Pipeline_TD1_testsJenkinsSmee/backend') {
                    sh 'git log -n 1'
                    sh 'sudo mvn clean package'
                    sh 'sudo mvn test'
                }
            }
        }
    }
}
