     def USER_NAME =""
     def committerEmail =""
     def user = ""
pipeline {
  agent any
   //env.JAVA_HOME= tool name: 'myjava', type: 'jdk'
    
    //def mavehHome= tool name: 'myMaven', type: 'maven'
    
   // def mvnCMD= "${mavehHome}/bin/mvn"
//  script{
    // def USER_NAME =""
     //def committerEmail =""
    // def user = ""
 // }
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
         rtServer (
          id: 'artifactory',
          url: 'http://34.93.184.75:8081/artifactory',
          // If you're using username and password:
          username: 'admin',
          password: 'password'
         // If you're using Credentials ID:
       // credentialsId: 'ccrreeddeennttiiaall'
      // If Jenkins is configured to use an http proxy, you can bypass the proxy when using this Artifactory server:
        //bypassProxy: true
      // Configure the connection timeout (in seconds).
       // The default value (if not configured) is 300 seconds:
       // timeout = 300
     )
        rtUpload (
         serverId: 'artifactory',
         spec: """{
                    "files": [{
                       "pattern": "/${WORKSPACE}/target/*.war",
                       "target": "example-repo-local/"
                    }]
                 }"""
 
    // Optional - Associate the downloaded files with the following custom build name and build number,
    // as build dependencies.
    // If not set, the files will be associated with the default build name and build number (i.e the
    // the Jenkins job name and number).
   // buildName: 'holyFrog',
   // buildNumber: '42'
   )
      }
    }
    
    stage('Downloding Artifacts') {
      steps {
         rtServer (
          id: 'artifactory',
          url: 'http://34.93.184.75:8081/artifactory',
          // If you're using username and password:
          username: 'admin',
          password: 'password'
         // If you're using Credentials ID:
       // credentialsId: 'ccrreeddeennttiiaall'
      // If Jenkins is configured to use an http proxy, you can bypass the proxy when using this Artifactory server:
        //bypassProxy: true
      // Configure the connection timeout (in seconds).
       // The default value (if not configured) is 300 seconds:
       // timeout = 300
     )
        rtDownload (
         serverId: 'artifactory',
         spec: """{
                    "files": [{
                       "pattern": "example-repo-local/*.war",
                       "target": "latest/opt/tomcat/latest-artifactory/"
                    }]
                 }"""
 
    // Optional - Associate the downloaded files with the following custom build name and build number,
    // as build dependencies.
    // If not set, the files will be associated with the default build name and build number (i.e the
    // the Jenkins job name and number).
   // buildName: 'holyFrog',
   // buildNumber: '42'
   )
      }
    }
    
   stage('Deploy') {
      steps {
        sh label: '', script: '''cd /var/lib/jenkins/workspace/sparkjava-war-example_master/latest/opt/tomcat/latest-artifactory/
        cp -f *sparkjava-hello-world*.war /opt/tomcat/latest/webapps/'''
      }
    }
    
   stage('Notify') {
      steps {
        script{
          user=sh (
      script: 'git --no-pager show -s --format=\'%ae\'',
      returnStdout: true
     ).trim()
    echo "Code commiter Name: ${user}"
    
     
   /*  wrap([$class: 'BuildUser']) {
          script {
             USER_NAME = sh "${BUILD_USER}"
             echo USER_NAME
          }
        }
         echo "Build user Name: ${env.USER_NAME}"  
      echo USER_NAME
      <p style="color:blue">Job trigger by user:  '${USER_NAME}'</p>
        */
 
    
     
        }
         emailext (
          subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
          body: """<p style="color:blue">Git commit trigger by user:  '${user}'</p>
            <p style="color:blue">SUCCESSFUL: Job Name '[${env.JOB_NAME}] Build Number :[${env.BUILD_NUMBER}] Jenkins User :[${USER_NAME}]':</p>
            <p style="color:blue">Check Build output logs at  <a href='${env.BUILD_URL}execution/node/3/ws/buildOutput.txt'>Build logs</a> </p>
            <p style="color:blue">Check console output at  <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a> </p>""",to: 'devdevops117@gmail.com',
          recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
      }
    }
    
  }
}
