package tn.esprit.spring;

import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.controller.RestControlTimesheet;
import tn.esprit.spring.controller.RestControlEntreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.MissionRepository;



@RunWith(SpringRunner.class)
@SpringBootTest()
public class MissionTest {


	private static final Logger l = LogManager.getLogger(MissionTest.class);	

	@Autowired
	RestControlTimesheet restControlTimesheet;
	
	@Autowired
	RestControlEntreprise restControlEntreprise;
	 
	@Autowired
	MissionRepository rep;


	@Test
	public void testAjouterMission()
	{
		Mission mission=new Mission("testAjout", "testAjout");
		try {
			l.info("In testAjouterMission():");
			int idmission=restControlTimesheet.ajouterMission(mission);
			
			l.info("Tester que l'id de mission est not null");
			assertNotNull(idmission);
			
			l.info("Tester que la longueur de Nom est supérieur à 0");
			assertTrue(mission.getName().length() >0);

			l.info("Tester que la longueur de Description est supérieur à 0");
			assertTrue(mission.getDescription().length() >0);
			
			l.info("Out testAjouterMission() sans erreurs.");
			
			

		}
		catch (Exception e)
		{ l.error(()->"Erreur dans testAjouterMission() : " + e); }
	}
	
	@Test
	public void testaffecterMissionADepartement()
	{
		try {

			l.info("In testAffecterMissionADepartement():");
			l.info("Je vais creer un Departement.");
			Departement departement=new Departement("Ressources Humaines.");
			l.info("Je vais creer une mission.");
			Mission mission=new Mission("testAjout", "testAjout");
			l.info("Je vais ajouter le departement.");
			int ajouterdepartement=restControlEntreprise.ajouterDepartement(departement);
			l.info("Je vais ajouter la mission.");
			int d=restControlTimesheet.ajouterMission(mission);
			l.info("Je vais affecter la mission au departement.");
			restControlTimesheet.affecterMissionADepartement(d, ajouterdepartement);
			l.info("Je vais reprendre la mission depuis la base de donnée.");
			Mission findbyid=rep.findById(d).orElseThrow(RuntimeException::new);
			l.info("Je vais tester si le departement_id du mission est égale a l'id de departement auquel je l'ai affecté.");
			assertTrue(findbyid.getDepartement().getId()==ajouterdepartement);
			l.info("Out testAffecterDepartement() sans erreurs.");
			
			
			
		}
		catch (Exception e)
		{ l.error(()->"testaffecterMissionADepartement() : " + e); }
	}


}




