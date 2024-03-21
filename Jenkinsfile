pipeline {
    agent {
        label 'agent1'
    }

    stages {
        stage('Build and Test') {
            steps {
                dir('/home/teamg/isa-devops-23-24-team-g-24/backend') {
                    sh 'mvn clean package'
                    sh 'mvn test'
                }
            }
        }
    }
}
