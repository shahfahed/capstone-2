pipeline {
    agent any
    stages {
        
        stage('checkout-scm') {
            steps {
                git branch: 'main', url: 'https://github.com/shahfahed/capstone-2.git'
            }
        }

        stage('build-push-dockerhub') {
            steps {
                withDockerRegistry(credentialsId: 'docker_hub', url: 'https://index.docker.io/v1/') {
                sh 'docker build --file Dockerfile --tag shahfahed/capstone-2:$BUILD_NUMBER .'
                sh 'docker push shahfahed/capstone-2:$BUILD_NUMBER'
                }
            }
        }

        stage('deploy on k8s') {
            steps {
                sshagent(['ssh_key']) {
                    sh 'ansible-playbook deploy.yaml -u ubuntu --extra-vars "build=$BUILD_NUMBER"'
                }                
            }
        }

    }
}