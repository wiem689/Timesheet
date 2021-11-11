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
       

         stage( 'Checkout  GIT' ){
                       steps{
                          echo 'Pulling ... ';
                               git branch:  'Wiem' ,
                              url :'https://github.com/wiem689/Timesheet.git'
                              }
                    }

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

                script { 

                    dockerImage = docker.build registry + ":$BUILD_NUMBER" 

                }

            } 

        }

        stage('Deploy our image') { 

            steps { 

                script { 

                    docker.withRegistry( '', registryCredential ) { 

                        dockerImage.push() 

                    }

                } 

            }

        } 
       stage('Cleaning up') { 

            steps { 

                bat "docker rmi $registry:$BUILD_NUMBER" 

            }
        } 
       
    }

}
