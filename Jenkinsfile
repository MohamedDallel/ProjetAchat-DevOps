pipeline {
    agent any
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
                script {
                    nexusArtifactUploader artifacts: [[artifactId: 'achat',
                                                      file: 'target/achat-1.0.jar',
                                                      type: 'jar']],
                                          nexusVersion:'nexus3',
                                          credentialsId: 'Nexus-Token',
                                          groupId: 'pom.tn.esprit.rh',
                                          nexusUrl: '192.168.2.109:8081',
                                          protocol: 'http',
                                          repository: 'maven-central-repository',
                                          version: 'pom.1.0'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build DOCKER_IMAGE_TAG
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                    sh 'docker login -u mohamedazizdallel -p Dalaz501099*** '
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
