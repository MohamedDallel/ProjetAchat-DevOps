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
                   sh 'docker build --no-cache -t achat:1.0 -f Dockerfile .'
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
        stage('Push Docker Image') {
            steps {
                    sh 'echo Kouki11630599* | docker login --username karymgharby --password-stdin'
                    sh 'docker tag achat:1.0 karymgharby/achat1:0'
                    sh 'docker push karymgharby/achat:1.0'
            }
        }
        stage('Deploy Services') {
            steps {
                script {
                    sh 'docker-compose up -d'
                }
            }
        }

        stage('Deploy Grafana and Prometheus') {
            steps {
                script {
                    // Restart existing Grafana and Prometheus containers
                    sh 'docker restart grafana'
                    sh 'docker restart prometheus4'
                }

    }
        }}

}
