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
                script {
                    sh 'mvn deploy'
                }
            }
        }
        }
 
}