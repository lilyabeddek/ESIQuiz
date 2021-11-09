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
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Apprenant;
import noyau.Formateur;

public class VisualiserApprenantsController implements Initializable {
	
	private Formateur formateur=Global.getFormateur();
	@FXML private AnchorPane panelStruct;
	
	@FXML private TableView<Apprenant> tableView;
	@FXML private TableColumn<Apprenant,String> nomColumn;
	@FXML private TableColumn<Apprenant,String> prenomColumn;
	@FXML private TableColumn<Apprenant,String> adresseColumn;
	@FXML private TableColumn<Apprenant,LocalDate> dateNaissanceColumn;
	
	

	@Override	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		nomColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("nom"));
		prenomColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("prenom"));
		adresseColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,String>("adresse"));
		dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<Apprenant,LocalDate>("date_naissance"));
		tableView.setItems(getApprenants());
		
		tableView.setEditable(true);
		nomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		prenomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		adresseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	

	private ObservableList<Apprenant> getApprenants() {
		ObservableList<Apprenant> apprenants = FXCollections.observableArrayList(formateur.getApprenants());
		return apprenants;
	}
	
	@FXML
	public void changerNomCell(CellEditEvent<Apprenant,String> e) {
		Apprenant p= tableView.getSelectionModel().getSelectedItem();
		p.setNom(e.getNewValue().toString());
	}
	@FXML
	public void changerPrenomCell(CellEditEvent<Apprenant,String> e) {
		Apprenant p= tableView.getSelectionModel().getSelectedItem();
		p.setPrenom(e.getNewValue().toString());
	}
	@FXML
	public void changerAdresseCell(CellEditEvent<Apprenant,String> e) {
		Apprenant p= tableView.getSelectionModel().getSelectedItem();
		p.setAdresse(e.getNewValue().toString());
	}
	
	@FXML
	public void supprimerApprenant(ActionEvent e) {
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Vous n'avez encore inscrit aucun apprenant à la formation !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner un apprenant avant !");
			alert.showAndWait();
		}
		else {
			ObservableList<Apprenant> apprenants,lignesSelectionnes;
			apprenants=tableView.getItems();
			lignesSelectionnes=tableView.getSelectionModel().getSelectedItems();
			
			for(Apprenant p : lignesSelectionnes) {
				formateur.supprimer_apprenant(p);
				apprenants.remove(p);
			}
			
		}
		
	}
	@FXML
	public void ajouterApprenant(ActionEvent e){
		createPage("/fxmlFilesFormateur/AjouterApprenant.fxml");
	}

	@FXML
	public void ModifierApprenant(ActionEvent e) throws IOException{
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Vous n'avez encore inscrit aucun apprenant à la formation !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner un apprenant avant !");
			alert.showAndWait();
		}
		else {

			 FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/ModifCompteApprenant.fxml"));
			 Node home = loader.load();
	         ModifierApprenantController controller = loader.getController();	    
	         controller.initApprenant(tableView.getSelectionModel().getSelectedItem());;
	         setNode(home);
	       	
		}

	}
	
	@FXML
	public void trierApprenants(ActionEvent e) {
		createPage("/fxmlFilesFormateur/TrierApprenants.fxml");
	}
	
	@FXML
	public void afficherActivites(ActionEvent e) throws IOException {
		
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Vous n'avez encore inscrit aucun apprenant à la formation !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner un apprenant avant !");
			alert.showAndWait();
		}
		else {
			 FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/ActivitesApprenant.fxml"));
			 Node home = loader.load();
	         ActivitesApprenantController controller = loader.getController();	
	         controller.initApprenant(tableView.getSelectionModel().getSelectedItem());;
	         setNode(home);
			
		}
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
