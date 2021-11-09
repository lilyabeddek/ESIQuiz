package controllersFormateur;

import java.io.IOException;
import java.time.LocalDate;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Apprenant;
import noyau.Qapprenant;

public class ActivitesApprenantController {
	

	    @FXML private AnchorPane panelStruct;
    	@FXML private Label nomField;
	    @FXML private Label prenomField;
	    @FXML private Label adresseField;
	    @FXML private Label dateNaissanceField;
	 
	    @FXML private TableView<Qapprenant> tableView;
		@FXML private TableColumn<Qapprenant,String> nomColumn;
		@FXML private TableColumn<Qapprenant,Float> pAccomplissement;
		@FXML private TableColumn<Qapprenant,Float> pReussite;
		@FXML private TableColumn<Qapprenant,LocalDate> dateOuvertureColumn;
		@FXML private TableColumn<Qapprenant,LocalDate> dateExpirationColumn;
	
	
     public void initApprenant(Apprenant apprenant) { 
 
		 nomField.setText(apprenant.getNom());
		 prenomField.setText(apprenant.getPrenom());
		 adresseField.setText(apprenant.getAdresse());
		 dateNaissanceField.setText(apprenant.getDate_naissance().toString());
		 

		 nomColumn.setCellValueFactory(new PropertyValueFactory<Qapprenant,String>("nom"));
	     pAccomplissement.setCellValueFactory(new PropertyValueFactory<Qapprenant,Float>("p_accomplissement"));
	     pReussite.setCellValueFactory(new PropertyValueFactory<Qapprenant,Float>("p_reussite"));
		 dateOuvertureColumn.setCellValueFactory(new PropertyValueFactory<Qapprenant,LocalDate>("dateDebut"));
		 dateExpirationColumn.setCellValueFactory(new PropertyValueFactory<Qapprenant,LocalDate>("dateFin"));

		 ObservableList<Qapprenant> listQuizs = FXCollections.observableArrayList(apprenant.getQuizs());
		 tableView.setItems(listQuizs);		
		 tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
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
