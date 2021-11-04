pipeline {	

	agent any 
	
	environment { 
        registry = "wiem689/Timesheet" 
       
    }

	stages{
			
			stage('Clean Package'){
					steps{
						bat "mvn clean package"
					}				
				}
				
			stage('Sonar Analyse'){
				steps{
                    bat "mvn sonar:sonar"
                  }
            }
		
			
		
			
			

			
		}
	}