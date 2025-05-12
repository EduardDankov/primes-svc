pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Checkstyle') {
            steps {
                script {
                    try {
                        sh './gradlew clean checkstyleMain checkstyleTest'
                    } catch (Exception e) {
                        error "Checkstyle failed. Check the logs for details."
                    }
                }
            }
        }
        stage('PMD') {
            steps {
                script {
                    try {
                        sh './gradlew clean pmdMain pmdTest'
                    } catch (Exception e) {
                        error "PMD failed. Check the logs for details."
                    }
                }
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