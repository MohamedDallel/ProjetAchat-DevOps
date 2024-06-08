pipeline {
    agent any
    stages {
         stage('GIT') {
            steps {
               checkout scmGit(branches: [[name: '*/BranchAziz']], extensions: [], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/MohamedDallel/ProjetAchat-DevOps.git']])
            }
        }
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
