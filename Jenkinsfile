pipeline {
    agent any

    tools {
        maven 'Maven 3'       // Set in Jenkins > Global Tool Config
        nodejs 'NodeJS 18'    // Set in Jenkins > Global Tool Config
    }

    environment {
        BACKEND_DIR = 'backend'
        FRONTEND_DIR = 'frontend'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Cloning source code..."
                checkout scm
            }
        }

        stage('Build Backend') {
            steps {
                dir("${env.BACKEND_DIR}") {
                    echo "Building Spring Boot backend..."
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir("${env.FRONTEND_DIR}") {
                    echo "Installing Angular dependencies..."
                    sh 'npm install'
                    echo "Building Angular frontend..."
                    sh 'ng build --configuration production'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                echo "Building Docker images..."
                dir("${env.BACKEND_DIR}") {
                    sh 'docker build -t myapp-backend .'
                }
                dir("${env.FRONTEND_DIR}") {
                    sh 'docker build -t myapp-frontend .'
                }

                // Optional: Push to Docker Hub
                // sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                // sh 'docker push myapp-backend'
                // sh 'docker push myapp-frontend'
            }
        }

        stage('Deploy (Optional)') {
            steps {
                echo "Deploying using Docker..."
                sh 'docker run -d -p 8080:8080 --name backend myapp-backend'
                sh 'docker run -d -p 4200:80 --name frontend myapp-frontend'
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed."
        }
    }
}
