package controllersFormateur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Apprenant;
import noyau.Formateur;

public class TrierApprenantsController implements Initializable {
	
	private Formateur formateur=Global.getFormateur();
	
	@FXML private AnchorPane panelStruct;
	@FXML private TableView<Apprenant> tableView;
	@FXML private TableColumn<Apprenant,String> nomColumn;
	@FXML private TableColumn<Apprenant,String> prenomColumn;
	@FXML private TableColumn<Apprenant,String> adresseColumn;
	@FXML private TableColumn<Apprenant,LocalDate> dateNaissanceColumn;
	@FXML private TableColumn<Apprenant,Float> moyenneColumn;
	
	@Override	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		nomColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("nom"));
		prenomColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("prenom"));
		adresseColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("adresse"));
		dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,LocalDate>("date_naissance"));
		moyenneColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,Float>("moyenne"));
		tableView.setItems(getApprenants());
		
	}
	

	private ObservableList<Apprenant> getApprenants() {
		ObservableList<Apprenant> apprenants = FXCollections.observableArrayList(formateur.afficher_classement ());
		return apprenants;
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
