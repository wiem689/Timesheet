pipeline {	

	agent any 
	
	environment { 
        registry = "Zaineb/Timesheet" 
       
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