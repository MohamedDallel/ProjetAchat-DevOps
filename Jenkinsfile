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
		  stage('Build Docker Image') {
            steps {
                script {
                    docker.build DOCKER_IMAGE_TAG
                }
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
		stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry(DOCKER_REGISTRY_URL, DOCKER_REGISTRY_CREDENTIALS) {
                        docker.image(DOCKER_IMAGE_TAG).push()
                    }
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
		stage('Upload to Nexus') {
            steps {
                script {
                    nexusArtifactUploader artifacts: [[artifactId: 'achat',
                                                      file: 'target/achat-1.0.jar',
                                                      type: 'jar']],
                                          nexusVersion:'nexus3',
                                          credentialsId: 'nexus-token',
                                          groupId: 'pom.tn.esprit.rh',
                                          nexusUrl: '192.168.2.53:8081',
                                          protocol: 'http',
                                          repository: 'maven-repository',
                                          version: 'pom.1.0'
                }
            }
        }
    }
}