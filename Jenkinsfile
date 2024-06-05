pipeline {
    agent any
    tools  {
        maven 'M2_HOME'
    }
    environment {
        DOCKER_IMAGE_TAG = "achat:1.0.0"
        DOCKER_REGISTRY_URL = "https://hub.docker.com/"
        DOCKER_REGISTRY_CREDENTIALS = "docker-token"
    }
    stages {
          stage('Maven Clean & Compile') {
            steps {
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
         stage('Upload to Nexus') {
            steps {
               sh 'mvn deploy'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build DOCKER_IMAGE_TAG
                }
            }
        }
         stage('Deploy Services') {
            steps {
                script {
                    sh 'docker compose up -d'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY_URL, DOCKER_REGISTRY_CREDENTIALS) {
                        docker.image(DOCKER_IMAGE_TAG).push()
                    }
                }
            }
        }
        stage('Mockito Tests') {
            steps {
                sh 'mvn test'
            }
        }
      
    }
}
