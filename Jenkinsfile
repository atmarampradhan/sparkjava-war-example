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
     stage('Uploding Artifacts') {
      steps {
         def server = Artifactory.newServer url: 'http://34.93.184.75:8081/artifactory', username: 'admin', password: 'password'
                 def uploadSpec = """{
                    "files": [{
                       "pattern": "/${WORKSPACE}/target/*.war",
                       "target": "example-repo-local/"
                    }]
                 }"""

                 server.upload(uploadSpec)
      }
    }
    
     stage('Downloading Artifacts') {
      steps {
         def server = Artifactory.newServer url: 'http://34.93.184.75:8081/artifactory', username: 'admin', password: 'password'
                 def downloadSpec = """{
                    "files": [{
                       "pattern": "example-repo-local/*.war",
                       "target": "latest/opt/tomcat/latest-artifactory/"
                    }]
                 }"""

                server.download(downloadSpec)
      }
    }
    
  }
}
