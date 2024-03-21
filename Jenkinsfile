pipeline {
    agent {
        label 'agent1'
    }

    stages {
        stage('Build and Test') {
            steps {
                script {
                    // Utiliser pushd pour naviguer dans le répertoire backend
                    pushd '/home/teamg/isa-devops-23-24-team-g-24/backend'

                    // Exécuter les commandes git et maven
                    sh 'git log -n 1'
                    sh 'sudo mvn clean package'
                    sh 'sudo mvn test'

                    // Revenir au répertoire précédent après avoir terminé les étapes
                    popd()
                }
            }
        }
    }
}
