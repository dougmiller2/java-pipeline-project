pipeline
{
    agent
    {
        label 'master'
    }

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
            steps
            {
                sh 'echo "Performing unit testing with junit"'
                sh 'ant -f test.xml -v'
                junit 'reports/result.xml'
            }
        }

        stage('build')
        {
            steps
            {
                sh 'echo "Calling Ant to compile the source code"'
                sh 'ant -f build.xml -v'
            }
        }

        stage('deploy')
        {
            steps
            {
                sh 'echo "Deploying the software"'
                sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
            }
        }
    }

    post
    {
        always
        {
            archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
    }
}
