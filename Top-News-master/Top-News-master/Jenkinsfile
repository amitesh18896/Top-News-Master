pipeline {
    agent any

    stages {
        stage ("Add executable permissions to files") {
            steps {
                sh 'chmod +x ci-scripts/build.sh'
                sh 'chmod +x ci-scripts/publish.sh'
                sh 'chmod +x gradlew'
            }
        }

        stage("Build APK") {
            steps {
                sh 'ci-scripts/build.sh'
            }
        }

        stage('Slack it'){
            steps {
                slackSend channel: '#android-builds',
                        message: 'build success'
            }
        }
    }
}