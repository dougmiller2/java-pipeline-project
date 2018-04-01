pipeline
{
    agent none

    environment
    {
        MAJOR_VERSION = 11
    }

    options
    {
        buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
    }

    stages
    {
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
                sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
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
                sh "wget http://doug-miller1.mylabserver.com/rectangles/all/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
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
                sh "wget http://doug-miller1.mylabserver.com/rectangles/all/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
                sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 35 45"
            }
        }

        stage('Promoting succesful project to Green')
        {
            agent
            {
                label 'apache'
            }

            steps
            {
                sh "echo 'All stages completed successfully.  Promoting rectangle_$env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar to Green.'"
                sh "cp /var/www/html/rectangles/all/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
            }
        }
    }
}
