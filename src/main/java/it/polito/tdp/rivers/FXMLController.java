/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Event;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private Simulator sim = new Simulator();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void handleSceltaFiume(ActionEvent event) {
    	if(this.boxRiver.getValue()!=null) {
    		River river = this.boxRiver.getValue();
    		model.riempiCampi(river);
    		//riempiti i campi mo li inseriamo!
    		this.txtStartDate.setText(model.getPrimaMisurazione().toString());
    		this.txtEndDate.setText(model.getUltimaMisurazione().toString());
    		this.txtNumMeasurements.setText(""+model.getNumeroMisurazioni());
    		this.txtFMed.setText(""+model.getFmed());
    	}
    }
    
    @FXML
    void handleSimula(ActionEvent event) {
    	double k = 0;
    	try {
    		k = Double.parseDouble(this.txtK.getText());
    	}
    	catch(NumberFormatException nfe) {
    		this.txtResult.setText("Inserire un numero come fattore di scala!");
    	}
    	if(k<=0) {
    		this.txtResult.appendText("Inserire un numero positivo!!");
    		return;
    	}
    	
    //	double Q = k*model.getFmed()*30;
    //	System.out.println("Il valore di Q è: "+Q);
    	sim.init(k,this.boxRiver.getValue());
    	sim.run2(model.getMisurazioniPerFiume());
    	int giorniInsoddisfacienti = sim.getGiorniInsoddisfacenti();
    	double  mediaCapacità = sim.calcolaMediaCapacita();
    	
    	this.txtResult.appendText("I giorni in cui la richiesta non è stata soddisfatta: "+giorniInsoddisfacienti+"\n");
    	this.txtResult.appendText("Il livello medio del periodo è: "+mediaCapacità+"\n");
    	
    	for(Event e : sim.getQueue()) {
    		this.txtResult.appendText(e.toString()+"\n");
    	}
    	
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxRiver.getItems().addAll(model.getAllRivers());
    }
}
