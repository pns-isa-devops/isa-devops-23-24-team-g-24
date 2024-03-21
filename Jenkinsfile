pipeline {
    agent {
        label 'agent1'
    }

    stages {
        stage('Build and Test') {
            steps {
                dir('/home/teamg/isa-devops-23-24-team-g-24') {
                    sh 'git log -n 1'
                    sh" cat ./src/test/java/com/isa/isa_devops_23_24_team_g_24/ActivityServiceTest.java"
                    sh 'sudo mvn clean package'
                    sh 'sudo mvn test'
                }
            }
        }
    }
}
