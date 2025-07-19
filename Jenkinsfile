pipeline {
    agent any

    tools {
        maven 'Maven 3'       // Set in Jenkins > Global Tool Config
        nodejs 'NodeJS 18'    // Set in Jenkins > Global Tool Config
    }

    environment {
        BACKEND_DIR = 'Rasika-backend'
        FRONTEND_DIR = 'employee-management-frontend'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "https://github.com/rasikapotale/employeeMgtSystem.git"
                checkout scm
            }
        }

        stage('Build Spring Boot backend') {
           steps {
               echo 'Building Spring Boot backend...'
                   dir('Rasika-backend/Employee') {
                  sh 'mvn clean package -DskipTests'
                                                  }
                }
        }

        stage('Build React Frontend') {
    steps {
       dir('employee-management-frontend/employee-management-frontend') {
            sh 'ls -l'
            sh 'ls -l src'
            echo 'Installing frontend dependencies...'
            sh 'npm install'

            echo 'Building React frontend...'
            sh 'npm run build'
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
