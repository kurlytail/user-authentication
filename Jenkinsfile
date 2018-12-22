pipeline {

    agent none

    parameters {
        string(defaultValue: "0.0", description: 'Build version prefix', name: 'BUILD_VERSION_PREFIX')
        string(defaultValue: "", description: 'Build number offset', name: 'BUILDS_OFFSET')
    }

    triggers {
        snapshotDependencies()
        upstream(upstreamProjects: 'kurlytail/utility-lib/master', threshold: hudson.model.Result.SUCCESS)
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
                    env['NPM_VERSION_NUMBER'] = getNpmVersion 'kurlytail/user-authentication/master', params.BUILD_VERSION_PREFIX, params.BUILDS_OFFSET
                	currentBuild.displayName = env['MAVEN_VERSION_NUMBER']
                }
            }
        }

        stage ('Maven Build') {
            agent {
                label 'mvn'
            }

            steps {
                sh 'rm -rf *'

                checkout scm

                // Update versions first
                sh '/usr/local/bin/mvn --batch-mode release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=$MAVEN_VERSION_NUMBER'

                withMaven (
                 	maven: "Maven",
                 	options: [
	                	dependenciesFingerprintPublisher(),
	                	concordionPublisher(),
	                	pipelineGraphPublisher(),
	                	artifactsPublisher(disabled: true)
                	]
                ) {
		            sh 'mvn -s settings.xml clean deploy --update-snapshots'
		            //writeFile file: '.archive-jenkins-maven-event-spy-logs', text: ''
		        }
            }
        }

        stage ('NPM Build') {
            agent {
                label 'node-build'
            }

            steps {
                sh 'rm -rf *'

                checkout scm

                nodejs(nodeJSInstallationName: 'Node') {
                    sh 'npm install'
                    sh 'npm version $NPM_VERSION_NUMBER'
                    sh 'npm run lint'
                    sh 'npm run test'
                    publishHTML target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'coverage',
                        reportFiles: 'index.html',
                        reportName: 'Coverage Report'
                    ]
                    junit 'test-report.xml'
                    sh 'npm run build'
                    sh 'npm publish'
                }
            }
        }
    }
}

