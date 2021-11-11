package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);
	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	public int ajouterMission(Mission mission) {
		try{
			l.debug("lancement de l'ajout d'une mission");
			missionRepository.save(mission);
			l.info("Mission est ajouté avec succès");
		}
		catch(Exception e){
			l.error("Erreur dans l'ajout de la mission ajouterMission()!!"+ e);
		}
		finally{
			l.info("la méthode ajouterMission() est finie");
		}
	
		return mission.getId();
		
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		try{
			
		l.debug("lancement de la méthode affecterMissionADepartement");
		Optional<Mission> mission = missionRepository.findById(missionId);
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if (mission.isPresent() && dep.isPresent()) {
			Mission m =mission.get();
			m.setDepartement(dep.get());
			missionRepository.save(m);
		}
		
		l.info("Département est affectée à une mission avec succès");
		}
		catch(Exception e){
			l.error("Erreur dans la méthode affecterMissionADepartement()!!"+ e);
		}
		finally{
			l.info("la méthode affecterDepartementAEntreprise() est finie");
		}
		
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		try{
			l.debug("lancement de l'ajout d'un Timesheet");
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		l.info("Timesheet est ajouté avec succès");
		}
		catch(Exception e){
			l.error("Erreur dans l'ajout du Timesheet ajouterTimesheet()!!"+ e);
		}
		finally{
			l.info("la méthode ajouterTimesheet est finie");
		}
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		l.info("In valider Timesheet");
		
		Optional<Employe> validateur = employeRepository.findById(validateurId);
		Optional<Mission> mission = missionRepository.findById(missionId);
		if (validateur.isPresent() && mission.isPresent() && !validateur.get().getRole().equals(Role.CHEF_DEPARTEMENT) ) {
			//verifier s'il est un chef de departement (interet des enum)	
			  l.warn("l'employe doit etre chef de departement pour valider une feuille de temps !");
				return;
			
		}
		//verifier s'il est le chef de departement de la mission en question
		boolean chefDeLaMission = false;
		if (validateur.isPresent() && mission.isPresent()){
		for(Departement dep : validateur.get().getDepartements()){
			if(dep.getId() == mission.get().getDepartement().getId()){
				chefDeLaMission = true;
				l.info("l'employe est bien le chef de department de la mission en question");
				break;
			}
		}
		}
		if(!chefDeLaMission){
			l.warn("l'employe doit etre chef de departement de la mission en question");
			return;
		}
//
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);

		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		l.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		l.debug("Recherche de tous les missions selon l'employee");	
	    l.info("Les missions affectés à l'employee d'id " +  employeId  +    "sont:" );
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
		
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		l.debug("Recherche de tous les employees qui partagent la meme mission");
		l.info("Les employees qui partagent la mission d'id " +  missionId  +   "sont:" );
		return timesheetRepository.getAllEmployeByMission(missionId);
	}

}
