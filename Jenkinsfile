pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.11'
        jdk 'JDK-17'
    }
    
    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out code from Git...'
                git branch: 'Web-Automation', 
                    url: 'https://github.com/Ashokaruldeva/Automation-Testing.git'
            }
        }
        
        stage('Run Automation Tests') {
            steps {
                echo 'Running automation tests...'
                script {
                    if (isUnix()) {
                        sh 'mvn clean test-compile test -Dheadless=true -Dmaven.main.skip=true'
                    } else {
                        bat 'mvn clean test-compile test -Dheadless=true -Dmaven.main.skip=true'
                    }
                }
            }
        }
        
        stage('Publish Reports') {
            steps {
                echo 'Publishing test reports...'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: '**/*_Report.html',
                    reportName: 'Automation Test Reports'
                ])
            }
        }
    }
    
    post {
        always {
            echo 'Sending email notification...'
            emailext (
                subject: "Automation Test Results: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                <h3>Test Execution Summary</h3>
                <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                <p><strong>Status:</strong> ${currentBuild.result ?: 'SUCCESS'}</p>
                <p><strong>View Reports:</strong> <a href="${env.BUILD_URL}Automation_Test_Reports/">Click Here</a></p>
                """,
                mimeType: 'text/html',
                to: 'ashokaruldeva@gmail.com'
            )
        }
    }
}