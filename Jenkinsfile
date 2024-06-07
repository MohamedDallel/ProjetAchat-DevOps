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
                    nexusArtifactUploader(
                        artifacts: [[artifactId: 'achat',
                                     file: 'target/achat-1.0.jar',
                                     type: 'jar']],
                        nexusVersion:'nexus3',
                        credentialsId: 'Nexus-Token',
                        groupId: 'pom.tn.esprit.rh',
                        nexusUrl: '192.168.133.136:8081',
                        protocol: 'http',
                        repository: 'maven-central',
                        version: 'pom.1.0'
                    )
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

    }

}
