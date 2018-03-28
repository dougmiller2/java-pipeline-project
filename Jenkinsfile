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
            }

            steps
            {
                sh 'ant -f build.xml -v'
            }
        }
    }
}
