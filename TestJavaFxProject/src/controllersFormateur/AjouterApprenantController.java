package controllersFormateur;


import java.io.IOException;
import java.time.LocalDate;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AjouterApprenantController {

	 @FXML private AnchorPane panelStruct;
	 @FXML private TextField nomField;
	 @FXML private TextField prenomField;
	 @FXML private TextField adresseField;
	 @FXML private DatePicker dateNaissanceField;
	 
	
	   @FXML
		private void ajouterApprenant(ActionEvent event) throws IOException {
		    Alert alert =new Alert(AlertType.ERROR);
		    alert.setHeaderText(null);
		    alert.setTitle("Erreur");
		    
		   if(nomField.getText().equals("") ||  prenomField.getText().equals("") || dateNaissanceField.getValue()==null ||  adresseField.getText().equals("")) {	   
			    
			    alert.setContentText("L'un des champs est vide, remplissez le avant de réeesayer!");
			    alert.showAndWait();
		   }
		   else if(dateNaissanceField.getValue().compareTo(LocalDate.now())>=0) {
			  
			    alert.setContentText("Date de naissance erronée !");
			    alert.showAndWait();
		   }
		   else {
			  
				   if(!Global.getFormateur().ajouter_apprenant(nomField.getText(), prenomField.getText(),dateNaissanceField.getValue(), adresseField.getText())) {
					   
					    alert.setContentText("Il existe deja un apprenant avec le même nom d'utilisateur et mot de passe generés");
					    alert.showAndWait();
				   }	    
				   else {
					   
     				    new Global().afficherNotif("Apprenant ajouté avec succès");     
					    System.out.println("ajout avec succee");		    
				   }

		   }
			
		}
	 
		@FXML
		private void revenirEnArriere(ActionEvent event) {
			createPage("/fxmlFilesFormateur/VisualiserApprenants.fxml");
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
}
