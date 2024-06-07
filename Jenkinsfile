pipeline {
    agent any
	environment {
        DOCKER_IMAGE_TAG = "achat:1.0.0"
        DOCKER_REGISTRY_URL = "https://index.docker.io/v1/"
        DOCKER_REGISTRY_CREDENTIALS = "docker-token"
    }
    stages {
          stage('Maven Clean & Compile') {
            steps {
                sh 'mvn clean install'
            }
        }
		stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
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

                sh 'docker build -t achatimage:v${BUILD_NUMBER} -f Dockerfile ./'
            }
        }
		stage('Push Docker Image') {
            steps {
                    sh 'docker login -u ademzikoaziz -p tarajidawla1919'
                    sh 'docker tag achatimage:v${BUILD_NUMBER} ademzikoaziz/achatimage:achatimage'
                    sh 'docker push ademzikoaziz/achatimage:achatimage'
            }
        }
		 stage('Deploy Services') {
            steps {
                script {
                    sh 'docker compose up -d'
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
}