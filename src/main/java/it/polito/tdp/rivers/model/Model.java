package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	RiversDAO dao;
	Map<Integer,River> fiumi;
	List<Flow> misurazioni;
	
	LocalDate primaMisurazione = null;
	LocalDate ultimaMisurazione = null;
	int numeroMisurazioni = 0;
	double Fmed;
	
	double K;
	
	public Model() {
		dao = new RiversDAO();
		fiumi = new HashMap<>();
	}

	public List<River> getAllRivers() {
		return dao.getAllRivers(fiumi);
	}

	public void riempiCampi(River river) {
		misurazioni = new ArrayList<>(dao.getTutteLeMisurazioniPerFimue(river,fiumi));
		river.setFlows(new ArrayList<Flow>(misurazioni));
		
		//Data prima misurazione
		primaMisurazione = misurazioni.get(0).getDay();
		//data ultima misurazione
		ultimaMisurazione = misurazioni.get(misurazioni.size()-1).getDay();
		//totale numero misurazioni
		numeroMisurazioni = misurazioni.size();
			
		//valore medio del flusso misurato Fmed
		double sommaFlow = 0;
		double contatore = 0;
		for(Flow f : misurazioni) {
			sommaFlow += f.getFlow();
			contatore++;
		}
		Fmed = sommaFlow/contatore;
		river.setFlowAvg(Fmed);
		System.out.println(Fmed);
	}

	public LocalDate getPrimaMisurazione() {
		return primaMisurazione;
	}

	public LocalDate getUltimaMisurazione() {
		return ultimaMisurazione;
	}

	public int getNumeroMisurazioni() {
		return numeroMisurazioni;
	}

	public double getFmed() {
		return Fmed;
	}

	public List<Flow> getMisurazioniPerFiume() {
		return misurazioni;
	}

	public void setK(double k2) {
		this.K = k2;
	}

	public double getK() {
		return K;
	}

}
