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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AjoutModifFormationController {
	
	
	@FXML private AnchorPane holdPane;
	@FXML private TextField titreField;
	@FXML private TextArea descriptionField;
	@FXML private DatePicker dateDebutField;
	@FXML private DatePicker dateFinField;
	@FXML private Button suivEnregistrer;
	@FXML private Button btnRevenirArriere;
	
	
	
	public void  setType(String type) {
		if(type.equals("modification")) {
			suivEnregistrer.setText("Enregistrer");
			titreField.setText(Global.getFormateur().get_formation().get_nom());
			descriptionField.setText(Global.getFormateur().get_formation().get_description());
			dateDebutField.setValue(Global.getFormateur().get_formation().get_datedeb());
			dateFinField.setValue(Global.getFormateur().get_formation().get_datefin());
			btnRevenirArriere.setVisible(true);
		}
	}
	//Enregistrer la creation/modification de la formation
	@FXML 
	private void suivantClick(ActionEvent e) throws IOException {
		
		if(suivEnregistrer.getText().equals("Suivant")) {
			
			if (titreField.getText().equals("") || descriptionField.getText().equals("") || dateDebutField.getValue()==null || dateFinField.getValue()==null) {
			    Alert alert =new Alert(AlertType.ERROR);
			    alert.setHeaderText(null);
			    alert.setTitle("Erreur");
			    alert.setContentText("L'un des champs est vide, remplissez le avant de réeesayer!");
			    alert.showAndWait();
			}
			else {
				
				if (dateDebutField.getValue().compareTo(LocalDate.now())<0) {
					Alert alert =new Alert(AlertType.ERROR);
				    alert.setHeaderText(null);
				    alert.setTitle("Erreur");
				    alert.setContentText("La date debut est erronée");
				    alert.showAndWait();
					
				}
				else if(dateDebutField.getValue().compareTo(dateFinField.getValue())>=0) {
					Alert alert =new Alert(AlertType.ERROR);
				    alert.setHeaderText(null);
				    alert.setTitle("Erreur");
				    alert.setContentText("La date de fin de la formation doit etre superieure à sa date debut ");
				    alert.showAndWait();
				}
				else {
					Global.getFormateur().set_formation(titreField.getText(),descriptionField.getText(),dateDebutField.getValue(),dateFinField.getValue());
					new Global().afficherNotif("Formation créée avec succès");  
					createPage("/fxmlFilesFormateur/VisualiserFormation.fxml");
				}
		
			}
			
		}
		else {
			
			if (titreField.getText().equals("") || descriptionField.getText().equals("") || dateDebutField.getValue()==null || dateFinField.getValue()==null) {
			    Alert alert =new Alert(AlertType.ERROR);
			    alert.setHeaderText(null);
			    alert.setTitle("Erreur");
			    alert.setContentText("L'un des champs est vide, remplissez le avant de réeesayer!");
			    alert.showAndWait();
			}
			else {
				
				if  ((dateDebutField.getValue().compareTo(Global.getFormateur().get_formation().get_datedeb())<0) && (Global.getFormateur().get_formation().get_datedeb().compareTo(LocalDate.now())<0)) {
					Alert alert =new Alert(AlertType.ERROR);
				    alert.setHeaderText(null);
				    alert.setTitle("Erreur");
				    alert.setContentText("La nouvelle date debut est erronée");
				    alert.showAndWait();
					
				}
				else if(dateDebutField.getValue().compareTo(dateFinField.getValue())>=0) {
					Alert alert =new Alert(AlertType.ERROR);
				    alert.setHeaderText(null);
				    alert.setTitle("Erreur");
				    alert.setContentText("La date de fin de la formation doit etre superieure à sa date debut ");
				    alert.showAndWait();
				}
				else {
					Global.getFormateur().get_formation().set_nom(titreField.getText());
					Global.getFormateur().get_formation().set_description(descriptionField.getText());
					Global.getFormateur().get_formation().set_datedeb(dateDebutField.getValue());
					Global.getFormateur().get_formation().set_datefin(dateFinField.getValue());
					new Global().afficherNotif("Formation modifiée avec succès");  
				}
		
			}
			
		}
	}
		
	
	@FXML 
	private void revenirArriere(ActionEvent e) {
		createPage("/fxmlFilesFormateur/VisualiserFormation.fxml");
	}
	
	
    private void setNode(Node node) {
		
		holdPane.getChildren().clear();
		holdPane.getChildren().add((Node)node);
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
			Node home= FXMLLoader.load(getClass().getResource(nomFichier));
			setNode(home);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
