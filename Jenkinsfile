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
                                          credentialsId: 'nexus-token',
                                          groupId: 'pom.tn.esprit.rh',
                                          nexusUrl: '192.168.50.4:8081',
                                          protocol: 'http',
                                          repository: 'maven-repository',
                                          version: 'pom.1.0'
                }
            }
        }
    }
}