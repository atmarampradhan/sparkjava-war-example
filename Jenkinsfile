pipeline {
  agent any
  stages {
    stage('Fetch') {
      steps {
        git(url: 'https://github.com/atmarampradhan/sparkjava-war-example.git', branch: 'master', poll: true)
      }
    }

  }
}