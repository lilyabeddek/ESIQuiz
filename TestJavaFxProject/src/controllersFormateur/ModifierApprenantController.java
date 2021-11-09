package controllersFormateur;

import noyau.*;
import java.io.IOException;
import java.time.LocalDate;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ModifierApprenantController {
	
	private Apprenant apprenant;
	 @FXML private AnchorPane panelStruct;
	 @FXML private TextField logInField;
	 @FXML private TextField passwordField;
	 @FXML private TextField nomField;
	 @FXML private TextField prenomField;
	 @FXML private TextField adresseField;
	 @FXML private DatePicker dateNaissanceField;
	 
	 
	 public void initApprenant(Apprenant apprenant) {
		 
		 this.apprenant=apprenant;
		 logInField.setText(this.apprenant.getUsername());
	     passwordField.setText(this.apprenant.getPasswd());
	     nomField.setText(this.apprenant.getNom());
	     prenomField.setText(this.apprenant.getPrenom());
	     adresseField.setText(this.apprenant.getAdresse());
		 dateNaissanceField.setValue(this.apprenant.getDate_naissance());
	 }
	 private void setNode(Node node) {
			
			panelStruct.getChildren().clear();
			panelStruct.getChildren().add((Node)node);
			FadeTransition ft= new FadeTransition(Duration.millis(1500));
			ft.setNode(node);
			ft.setFromValue(0.1);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.setAutoReverse(false);
			ft.play();
			
     }
		private void createPage(String nomFichier) {
		    try {	
				Node home = FXMLLoader.load(getClass().getResource(nomFichier));
				setNode(home);		
		    } catch (IOException e) {
				e.printStackTrace();
			}		
	 }
	 @FXML
		private void revenirEnArriere(ActionEvent event) {
			createPage("/fxmlFilesFormateur/VisualiserApprenants.fxml");
		}
	
	 @FXML
	 private void Enregistrer(ActionEvent event) throws IOException {
		 if(nomField.getText().equals("") ||  prenomField.getText().equals("") || dateNaissanceField.getValue()==null ||  adresseField.getText().equals("")) {	   
			    Alert alert =new Alert(AlertType.ERROR);
			    alert.setHeaderText(null);
			    alert.setTitle("Erreur");
			    alert.setContentText("L'un des champs est vide, remplissez le avant de réeesayer!");
			    alert.showAndWait();
		   }
		   else if(dateNaissanceField.getValue().compareTo(LocalDate.now())>=0) {
			   Alert alert =new Alert(AlertType.ERROR);
			    alert.setHeaderText(null);
			    alert.setTitle("Erreur");
			    alert.setContentText("Date de naissance erronée !");
			    alert.showAndWait();
		   }
		   else {
			    this.apprenant.setUsername(logInField.getText());
				this.apprenant.setPasswd(passwordField.getText());
				this.apprenant.setNom(nomField.getText());
				this.apprenant.setPrenom(prenomField.getText());
				this.apprenant.setAdresse(adresseField.getText());
				this.apprenant.setDate_naissance(dateNaissanceField.getValue());
				new Global().afficherNotif("Compte apprenant modifié avec succès");  
		   }
		
		
	 }
	


}
