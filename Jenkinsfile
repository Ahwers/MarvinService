pipeline {
    agent { docker { image 'maven:3.6.3-openjdk-11-slim' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}