pipeline
{
    agent any

    stages
    {
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
