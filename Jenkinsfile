pipeline {

       agent any
        tools{
        jdk "JDK 8"
        }
           stages{

             stage( 'Checkout  GIT' ){
                       steps{
                          echo 'Pulling ... ';
                              git branch:  'wiem' ,
                              url :'https://github.com/wiem689/Timesheet'
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
				  
				  stage("docker,build"){
               steps{

                   bat "FROM openjdk:8-jdk-alpine EXPOSE 8083 ADD target/docker-spring-boot.war docker-spring-boot.war  ENTRYPOINT["java","-jar","/docker-spring-boot.war"]"
                    }

                  
           }
}