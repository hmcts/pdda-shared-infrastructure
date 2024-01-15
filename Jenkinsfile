library identifier: 'jenkins-libraries@master', retriever: modernSCM(
   [$class: 'GitSCMSource',
    remote: 'git@git:common/jenkins-libraries.git',
   credentialsId: 'GitlabSSH']) _
   
def newVersion = "null"

pipeline {
    environment {
        https_proxy = "http://10.100.1.4:3128/"
        http_proxy = "http://10.100.1.4:3128/"
        no_proxy = "10.100.3.5, 10.100.3.4"
        registryName='pddacontainerRegistry'
        registryCredential='acr'
        registryUrl='pddacontainerregistry.azurecr.io'
        //These are for Mac M1/M2 only
/*         DOCKER_DEFAULT_PLATFORM = "linux/amd64"
        DOCKER_BUILDKIT = 0
        COMPOSE_DOCKER_CLI_BUILD = 0 */

//        scannerHome = tool name: 'SonarQube Scanner 3.3', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    }
//    agent any
    agent { label "cibackpdda" }
    stages {
        stage('Clean workspace'){
            steps{
                cleanWs()
            }
        }
        stage("Checkout") {
            steps {
                echo "Checking out code to get version number"
                checkout poll: false, 
                changelog: false,
                scm: [$class: 'GitSCM', branches: [[name: "${env.GIT_BRANCH}"]], 
                doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CheckoutOption', timeout: 60 ]], 
                userRemoteConfigs: [[credentialsId: 'GitlabSSH', url: 'git@git:pdda/pdda_v2.git']]]
                echo "Code checked out"
            }
        }
        stage ("UpdateVersionNumber") {
            steps {
                script {
                    def props = readJSON file: 'version.json'
                    def currentVersion = props['version']
                    def splitVersion = currentVersion.tokenize('.')
                    oldVersion = "${splitVersion[0]}.${splitVersion[1]}.${splitVersion[2]}".trim()
                    echo "old Version is : ${oldVersion}"
                    newVersion = "${splitVersion[0]}.${splitVersion[1]}.${BUILD_NUMBER}".trim()
                    echo "New version is : ${newVersion}"
                    } 
            }
        }
         //Ready for the Maven build
        stage("BuildApplication") {
            steps {
                    sh 'mvn clean install'
            }
        } 

        
        /* stage('deploy to nexus') {
            steps {
                nexusArtifactUploader (
                    nexusVersion:'nexus3',
                    protocol:'http',
                    nexusUrl:'10.100.3.5:7000',
                    groupId:'opt.moj',
                    version:"${newVersion}",
                    repository:'pdda',
                    credentialsId:'nexus-write',
                    artifacts:[
                        [artifactId:'pdda_1',
                        type:'war',
                        classifier:'',
                        file:"${WORKSPACE}\\.......war"],
                        [artifactId:'pdda_2',
                        type:'war',
                        classifier:'',
                        file:"${WORKSPACE}\\.......war"]
                    ]
                )
            }
        } */
    }


    post {
        success {
            script {
                echo "Starting post steps for version: ${newVersion}"
                withCredentials([usernamePassword(credentialsId: 'jenkins_pdda', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh '''
                        git config --global user.email "jenkins@clouddev.online"
                        git config --global user.name "jenkins_pdda"
                        git checkout $GIT_BRANCH
                    '''
                    //Read in the properties file containing the application version 
                    def oldVersionFile = readFile 'version.json'
                    //Update the version
                    def newVersionFile = oldVersionFile.replaceAll("${oldVersion}", "${newVersion}")
                    //Write it back out to the file
                    writeFile(file: 'version.json', text: newVersionFile)

                    //Read in k8s yaml file
                    def oldYamlFile = readFile 'helm/pdda-app/dev.pdda-app.values.yaml'
                    //Update version
                    def newYamlFile = oldYamlFile.replaceAll("${oldVersion}", "${newVersion}")
                    //Write it back
                    writeFile(file: 'helm/pdda-app/dev.pdda-app.values.yaml', text: newYamlFile)
                    sh '''
                        git add version.json
                        git add helm/pdda-app/dev.pdda-app.values.yaml
                        git commit -m "[ci-skip] Jenkins build of version ${newVersion}"
                        git tag ${newVersion}
                        git push http://$GIT_USERNAME:$GIT_PASSWORD@10.100.3.4/pdda/pdda_v2.git $GIT_BRANCH ${newVersion}
                    '''
                }
            }

            script {
                def pddaImage = docker.build("pdda:${newVersion}")
                //publish docker image tp JC docker registry
                //docker.withRegistry('http://docker.registry') {
                 //   pddaImage.push()
                // }
                 //Push docker image to ACR for use with AKS
                 docker.withRegistry("http://${registryUrl}", registryCredential) {
                    pddaImage.push()
                 }
                sh '''
                    helm upgrade pdda-app ./helm/pdda-app --values ./helm/pdda-app/dev.pdda-app.values.yaml

                '''
            }       
       }
    }
}
