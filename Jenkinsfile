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
                   sh 'docker build --no-cache -t achatimage:v${BUILD_NUMBER} -f Dockerfile ./'
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
                    sh 'docker login -u karymgharby -p Kouki11630599*'
                    sh 'docker tag achatimage:v${BUILD_NUMBER} karymgharby/achatimage:achatimage'
                    sh 'docker push karymgharby/achatimage:achatimage'
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
        }}

}
