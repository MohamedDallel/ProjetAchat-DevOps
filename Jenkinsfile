pipeline {
    agent any
    environment {
        DOCKER_REGISTRY_URL = "https://hub.docker.com/"
        DOCKER_REGISTRY_CREDENTIALS = "docker-token"
    }
    stages {
        stage('Maven Clean & Compile') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                   docker.build("karymgharby/achat:1.0")
                }
            }
        }

        stage('Upload to Nexus') {
            steps {
                script {
                    sh 'mvn deploy'
                    
                }
            }
        }
        stage('Deploy Services') {
            steps {
                script {
                    sh 'docker-compose up -d'
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
        stage('Deploy Grafana and Prometheus') {
            steps {
                script {
                    // Restart existing Grafana and Prometheus containers
                    sh 'docker restart grafana'
                    sh 'docker restart prometheus'
                }

    }
        }

}
