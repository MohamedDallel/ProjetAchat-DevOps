pipeline {
    agent any
    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git branch: 'branchadem',
                url :'https://github.com/MohamedDallel/ProjetAchat-DevOps'
            }
        }
          stage('Maven Clean & Compile') {
            steps {
                // Add your build steps here, e.g., Maven, Gradle, etc.
                sh 'mvn clean install'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    // Exécuter mvn sonar:sonar
                              sh "mvn clean package sonar:sonar"

                }
            }
        }
    }
}