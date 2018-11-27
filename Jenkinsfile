pipeline {

    agent none
    
    parameters {
        string(defaultValue: "0.0", description: 'Build version prefix', name: 'BUILD_VERSION_PREFIX')
        string(defaultValue: "", description: 'Build number offset', name: 'BUILDS_OFFSET')
    }

    stages {
        stage('Prepare env') {
            agent {
                label 'master'
            }
            
            steps {
                script {
                    loadLibrary()
                    env['MAVEN_VERSION_NUMBER'] = getMavenVersion 'kurlytail/user-authentication/master', params.BUILD_VERSION_PREFIX, params.BUILDS_OFFSET
                }
            }
        }
        
        stage ('Build') {
            agent {
                label 'mvn'
            }
            
            steps {
                sh 'rm -rf *'
     
                checkout scm
                withMaven (options: [
                	dependenciesFingerprintPublisher(disabled: false),
                	concordionPublisher(disabled: false),
                	artifactsPublisher(disabled: true),
                	pipelineGraphPublisher(disabled: false, lifecycleThreshold: "install")
                ]) {
		            sh '/usr/local/bin/mvn --batch-mode release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=$MAVEN_VERSION_NUMBER'
		            sh '/usr/local/bin/mvn -s settings.xml clean deploy --update-snapshots' 
		        }
            }
        }
    }
}

