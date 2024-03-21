pipeline {
    agent {
        label 'agent1'
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
