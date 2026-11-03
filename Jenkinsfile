pipeline {
    agent any
    stages {
        stage('Compilar') {
            steps {
                sh './mvnw -B -DskipTests compile'
            }
        }
        // TODO RN-3: agregar las etapas restantes del pipeline CI/CD.
    }
}
