pipeline {
    agent any
    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git branch: 'BranchAziz',
                url :'https://github.com/MohamedDallel/ProjetAchat-DevOps'
            }
        }
          stage('Maven Clean & Compile') {
            steps {
                // Add your build steps here, e.g., Maven, Gradle, etc.
                sh 'mvn clean install'
            }
        }
        stage('Mockito Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    // Exécuter mvn sonar:sonar
                              sh "mvn clean package sonar:sonar"

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
                                          credentialsId: 'Nexus-Token',
                                          groupId: 'pom.tn.esprit.rh',
                                          nexusUrl: '192.168.2.109:8081',
                                          protocol: 'http',
                                          repository: 'maven-central-repository',
                                          version: 'pom.1.0'
                }
            }
        }
    }
}
