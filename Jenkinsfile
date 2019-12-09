pipeline {
  agent any
   env.JAVA_HOME= tool name: 'myjava', type: 'jdk'
    
    def mavehHome= tool name: 'myMaven', type: 'maven'
    
    def mvnCMD= "${mavehHome}/bin/mvn"
  stages {
    stage('FetchGitCode') {
      steps {
        git(url: 'https://github.com/atmarampradhan/sparkjava-war-example.git', branch: 'master', poll: true)
      }
    }
   stage('Compile') {
      steps {
        sh "${mvnCMD} compile"
       echo "Maven packaging -DskipTests"
      }
    }
  }
}
