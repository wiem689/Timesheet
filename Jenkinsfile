pipeline { 

    environment { 

        registry = "wiemchalouati/imagedoc" 

        registryCredential = 'wiemid' 

        dockerImage = '' 

    }

    agent any 
    tools{
        jdk "JDK 8"
        }

    stages { 
       

       /*  stage( 'Checkout  GIT' ){
                       steps{
                          echo 'Pulling ... ';
                               git branch:  'Wiem' ,
                              url :'https://github.com/wiem689/Timesheet.git'
                              }
                    }*/

            stage("Test,Build"){
               steps{

                   bat "mvn clean install"
                    }

                  }

              stage("package"){
               steps{

                   bat "mvn package"
                    }

                  }
                  
               stage("Sonar"){
               steps{

                   bat "mvn sonar:sonar"
                    }

                  }
                  
                stage("Nexus"){
               steps{

                   bat "mvn deploy"
                    }

                  }
           

        stage('Building our image') { 

            steps { 
                
                bat "docker build -t wiemchalouati/imagedoc ."
                bat "docker tag wiemchalouati/imagedoc wiemchalouati/imagedoc:v$BUILD_NUMBER"
          

            } 

        }

        stage('Deploy our image') { 

            steps { 
                
                bat "docker push wiemchalouati/imagedoc:v$BUILD_NUMBER"
       

                } 

            }
       stage('Cleaning up') { 

            steps { 

                bat "docker rmi $registry:v$BUILD_NUMBER" 

            }
        } 
       
    }

}
