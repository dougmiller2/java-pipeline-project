pipeline
{
    agent any

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
    }

    post
    {
        always
        {
            archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
    }
}
