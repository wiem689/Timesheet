pipeline {

       agent any
        tools{
        jdk "JDK 8"
        }
           stages{

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
				  
				  stage("Docker"){
					steps{
						 bat "docker build -t wiemchalouati/timesheet ."
						 bat "docker run --name timesheet -p 8082:8082 wiemchalouati/timesheet "
						//bat "docker push wiemchalouati/timesheet"
					}
				  }
           }
}