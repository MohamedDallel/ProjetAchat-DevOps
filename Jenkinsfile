pipeline {
    agent any
     environment {
        DOCKER_IMAGE_TAG = "achat:1.0.0"
        DOCKER_REGISTRY_URL = "https://hub.docker.com/"
        DOCKER_REGISTRY_CREDENTIALS = "docker-token"
    }
    stages {
          stage('MAVEN BUILD') {
            steps {
                sh 'mvn clean install'
            }
        }

         stage('SONARQUBE') {
            steps {
                withSonarQubeEnv('SonarQube') {
                              sh "mvn clean package sonar:sonar"
                }
            }
        }
         stage('MOCKITO') {
            steps {
                sh 'mvn test'
            }
        }
        stage('NEXUS') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('DOCKER IMAGE') {
            steps {
              //  script {
              //      docker.build DOCKER_IMAGE_TAG
              //  }
                sh 'docker build --no-cache -t achatimage:v${BUILD_NUMBER} -f Dockerfile ./'
            }
        }

        stage('DOCKER HUB') {
            steps {
                    sh 'docker login -u mohamedazizdallel -p Dalaz501099***'
                    sh 'docker tag achatimage:v${BUILD_NUMBER} mohamedazizdallel/achatimage:achatimage'
                    sh 'docker push mohamedazizdallel/achatimage:achatimage'
            }
        }
                 stage('DOCKER-COMPOSE') {
            steps {
                script {
                    sh 'docker compose up -d'
                }
            }
        }
         stage('GRAGANA') {
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
