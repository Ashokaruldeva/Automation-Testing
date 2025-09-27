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
                subject: "🚀 Automation Test Results: ${currentBuild.result ?: 'SUCCESS'} - Build #${env.BUILD_NUMBER}",
                body: """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                    <h2 style="color: #4CAF50;">🎯 Test Execution Summary</h2>
                    <table style="border-collapse: collapse; width: 100%; border: 1px solid #ddd;">
                        <tr style="background-color: #f2f2f2;">
                            <td style="padding: 12px; border: 1px solid #ddd;"><strong>Job Name:</strong></td>
                            <td style="padding: 12px; border: 1px solid #ddd;">${env.JOB_NAME}</td>
                        </tr>
                        <tr>
                            <td style="padding: 12px; border: 1px solid #ddd;"><strong>Build Number:</strong></td>
                            <td style="padding: 12px; border: 1px solid #ddd;">#${env.BUILD_NUMBER}</td>
                        </tr>
                        <tr style="background-color: #f2f2f2;">
                            <td style="padding: 12px; border: 1px solid #ddd;"><strong>Status:</strong></td>
                            <td style="padding: 12px; border: 1px solid #ddd; color: #4CAF50; font-weight: bold;">${currentBuild.result ?: 'SUCCESS'}</td>
                        </tr>
                        <tr>
                            <td style="padding: 12px; border: 1px solid #ddd;"><strong>Build Duration:</strong></td>
                            <td style="padding: 12px; border: 1px solid #ddd;">${currentBuild.durationString}</td>
                        </tr>
                        <tr style="background-color: #f2f2f2;">
                            <td style="padding: 12px; border: 1px solid #ddd;"><strong>Timestamp:</strong></td>
                            <td style="padding: 12px; border: 1px solid #ddd;">${new Date()}</td>
                        </tr>
                    </table>
                    <br>
                    <div style="text-align: center;">
                        <a href="${env.BUILD_URL}Automation_Test_Reports/" style="background-color: #4CAF50; color: white; padding: 15px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; margin: 5px;">📊 View All Reports</a>
                        <br><br>
                        <a href="${env.BUILD_URL}Automation_Test_Reports/login_Report.html" style="background-color: #2196F3; color: white; padding: 15px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; margin: 5px;">🎯 Direct Login Report</a>
                        <br><br>
                        <a href="${env.BUILD_URL}execution/node/3/ws/reports/login/login_Report.html" style="background-color: #FF9800; color: white; padding: 15px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; margin: 5px;">📄 Workspace Report</a>
                    </div>
                    <br>
                    <p style="color: #666; font-size: 12px;">This is an automated message from Jenkins CI/CD Pipeline</p>
                </div>
                """,
                mimeType: 'text/html',
                to: 'ashokaruldeva@gmail.com'
            )
        }
    }
}