pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                script {
                    try {
                        sh './gradlew clean build'
                    } catch (Exception e) {
                        error "Build failed. Check the logs for details."
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    try {
                        sh './gradlew test'
                    } catch (Exception e) {
                        error "Tests failed. Check the logs for details."
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and tests completed successfully.'
        }
        failure {
            echo 'Build or tests failed. Check the logs for details.'
        }
    }
}