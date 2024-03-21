pipeline {
    agent {
        label 'agent1'
    }

    stages {
        stage('Build and Test') {
            steps {
                script {
                    // Utiliser pushd pour naviguer dans le répertoire backend
                    sh "cd backend"

                    // Exécuter les commandes git et maven
                    sh 'git log -n 1'
                    sh 'sudo mvn clean package'
                    sh 'sudo mvn test'


                }
            }
        }
    }
}
