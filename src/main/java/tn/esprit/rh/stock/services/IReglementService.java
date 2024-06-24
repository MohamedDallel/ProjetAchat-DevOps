package tn.esprit.rh.stock.services;

import java.util.Date;
import java.util.List;

import tn.esprit.rh.stock.entities.Reglement;

public interface IReglementService {

	List<Reglement> retrieveAllReglements();
	Reglement addReglement(Reglement r);
	Reglement retrieveReglement(Long id);
	List<Reglement> retrieveReglementByFacture(Long idFacture);
	float getChiffreAffaireEntreDeuxDate(Date startDate, Date endDate); 

}
