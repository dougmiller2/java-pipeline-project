pipeline
{
    agent none

    environment
    {
        MAJOR_VERSION = 13
    }

    options
    {
        buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
    }

    stages
    {
        stage('Greetings')
        {
            agent any

            steps
            {
                sayHello 'You Magnificent Bastard!'
            }
        }

        stage('Git Information')
        {
            agent any

            steps
            {
                echo "My Branch Name:  ${env.BRANCH_NAME}"

                script
                {
                    def myLib = new linuxacademy.git.gitStuff();

                    echo "My Commit:  ${myLib.gitCommit("${env.WORKSPACE}/.git")}"
                }
            }
        }

        stage('Unit Tests')
        {
            agent
            {
                label 'apache'
            }

            steps
            {
                sh 'echo "Performing unit testing with junit"'
                sh 'ant -f test.xml -v'
                junit 'reports/result.xml'
            }
        }

        stage('build')
        {
            agent
            {
                label 'apache'
            }

            steps
            {
                sh 'echo "Calling Ant to compile the source code"'
                sh 'ant -f build.xml -v'
            }

            post
            {
                success
                {
                    archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
                }
            }
        }

        stage('deploy')
        {
            agent
            {
                label 'apache'
            }

            steps
            {
                sh 'echo "Deploying the software"'
                sh "if ![ -d '/var/www/html/rectangles/all/${env.BRANCH_NAME}' ]; then mkdir /var/www/html/rectangles/all/${env.BRANCH_NAME}; fi"
                sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}/"
            }
        }

        stage('Running on CentOS')
        {
            agent
            {
                label 'CentOS'
            }

            steps
            {
                sh 'echo "Running the rectangle software on CentOS"'
                sh 'echo using wget to download jar from apache server'
                sh "wget http://doug-miller1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 30 40"
            }
        }

        stage('Running on Debian in Docker Container')
        {
            agent
            {
                docker 'openjdk:8u162-jre'
            }

            steps
            {
                sh 'echo "Running on Debian in a Docker container"'
                sh 'echo using wget to download jar from apache server'
                sh "wget http://doug-miller1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 35 45"
            }
        }

        stage('Promoting succesful project to Green')
        {
            agent
            {
                label 'apache'
            }

            when
            {
                branch 'master'
            }

            steps
            {
                sh "echo 'All stages completed successfully.  Promoting rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar to Green.'"
                sh "cp /var/www/html/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
            }
        }

        stage('Promote source code from Development branch to Master branch')
        {
            agent
            {
                label 'apache'
            }

            when
            {
                branch 'development'
            }

            steps
            {
                echo "Promoting the source code from Development branch to Master"
                echo "stashing any local changes"
                sh 'git stash'
                echo "Checking out development branch"
                sh 'git checkout development'
                echo "Checking out the master branch"
                sh 'git pull origin'
                sh 'git checkout master'
                echo "Merging development into master branch"
                sh 'git merge development'
                echo "Pushing to Origin master"
                sh 'git push origin master'
                echo "Tagging the release"
                sh "git tag rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
                sh "git push origin rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
            }

            post
            {
                success
                {
                    emailext(subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] Development Promoted to Master",
                      body: """<p>'${env.JOB_NAME} [${env.BUILD_NUMBER}]' Development Promoted to Master":</p>
                        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                      to: "to_doug@hotmail.com"
                    )
                }
            }
        }
    }

    post
    {
        failure
        {
            emailext(
              subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] Failed!",
              body: """<p>'${env.JOB_NAME} [${env.BUILD_NUMBER}]' Failed!":</p>
                <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
              to: "to_doug@hotmail.com"
            )
        }
    }
}
