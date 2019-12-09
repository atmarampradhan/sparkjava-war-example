pipeline {
  agent any
   //env.JAVA_HOME= tool name: 'myjava', type: 'jdk'
    
    //def mavehHome= tool name: 'myMaven', type: 'maven'
    
   // def mvnCMD= "${mavehHome}/bin/mvn"
  tools { 
        maven 'myMaven' 
        jdk 'myjava' 
    }
  stages {
    stage('FetchGitCode') {
      steps {
        git(url: 'https://github.com/atmarampradhan/sparkjava-war-example.git', branch: 'master', poll: true)
      }
    }
   stage('Compile') {
      steps {
        sh "mvn compile"
       echo "Maven packaging -DskipTests"
      }
    }
    stage('CodeReview') {
      steps {
        sh "mvn -batch-mode -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd"
        pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd.xml', unHealthy: ''
       echo "Pmd CodeReview"
      }
    }
     stage('build') {
      steps {
        sh "mvn package -DskipTests -l buildOutput.txt"
       echo "Maven building and creating war"
      }
    }
  }
}
