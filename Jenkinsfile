pipeline {
    agent any
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
                              sh "mvn clean package sonar:sonar"
                }
            }
        }
         stage('Mockito Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Upload to Nexus') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Build Docker Image') {
            steps {
              //  script {
              //      docker.build DOCKER_IMAGE_TAG
              //  }
                sh 'docker build --no-cache -t achatimage:v${BUILD_NUMBER} -f Dockerfile ./'
            }
        }

        stage('Push Docker Image') {
            steps {
                    sh 'docker login -u mohamedazizdallel -p Dalaz501099***'
                    sh 'docker tag achatimage:v${BUILD_NUMBER} mohamedazizdallel/achatimage:achatimage'
                    sh 'docker push mohamedazizdallel/achatimage:achatimage'
            }
        }
                 stage('Deploy Services') {
            steps {
                script {
                    sh 'docker compose up -d'
                }
            }
        }
       
      
    }
}
