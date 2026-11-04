pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    options {
        timeout(time: 20, unit: 'MINUTES')
    }
    stages {
        stage('Compilar') {
            steps {
                sh 'mvn -B -ntp compile'
            }
        }
        stage('Pruebas y cobertura') {
            steps {
                sh 'mvn -B -ntp verify'
            }
            post {
                always {
                    junit 'target/*-reports/*.xml'
                }
            }
        }
        stage('Publicar artefacto') {
            steps {
                archiveArtifacts artifacts: 'target/cuotafacil-*.jar', fingerprint: true
            }
        }
        stage('Desplegar a cert') {
            when {
                branch 'master'
            }
            steps {
                withCredentials([string(credentialsId: 'cuotafacil-deploy-token', variable: 'DEPLOY_TOKEN')]) {
                    sh 'bash devops/desplegar.sh cert target/cuotafacil-*.jar'
                }
            }
        }
    }
}
