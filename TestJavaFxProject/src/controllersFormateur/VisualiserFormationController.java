package controllersFormateur;


import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Formation;
import noyau.Notion;



public class VisualiserFormationController implements Initializable{
	
	
	private Formation formation=Global.getFormateur().get_formation();
	@FXML private AnchorPane holdPane;
	@FXML private TableView<Notion> tableView;
	@FXML private TableColumn<Notion,String> titreColumn;
	
	@FXML private Label titreFormation;
	@FXML private Label description;
	@FXML private Label dateDebut;
	@FXML private Label dateFin;
	@FXML private TextField titreNotion;
	
	@FXML private Button btnAfficherDetails;
	@FXML private Button btnModifierFormation;
	@FXML private Button btnSupprimerNotion;
	@FXML private Button btnAjouterNotion;
	@FXML private Label lblAjouterNotion;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		description.setWrapText(true);
		
		titreFormation.setText(formation.get_nom());
		description.setText(formation.get_description());
		dateDebut.setText(formation.get_datedeb().toString());
		dateFin.setText(formation.get_datefin().toString());
		
		titreColumn.setCellValueFactory(new PropertyValueFactory<Notion,String>("titre"));
		tableView.setItems(getNotions());
		if (formation.terminee()) {
			btnAjouterNotion.setDisable(true);
			lblAjouterNotion.setDisable(true);
			btnModifierFormation.setDisable(true);
			btnSupprimerNotion.setDisable(true);
		}
		else {
			tableView.setEditable(true);
			titreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		}
			
	}
	
	private ObservableList<Notion> getNotions() {
		ObservableList<Notion> notions = FXCollections.observableArrayList(formation.getNotions());
		return notions;
	}
	
	@FXML
	public void userClickedOnTable()
    {
		this.btnAfficherDetails.setDisable(false);
		this.btnSupprimerNotion.setDisable(false);      
    }
	
	@FXML
	public void changerTitreNotionCell(CellEditEvent<Notion,String> e) {
		
		Notion n= tableView.getSelectionModel().getSelectedItem();
		if (!formation.modifierTitreNotion(n, e.getNewValue().toString())) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Une notion avec le même titre existe déjà !");
			alert.showAndWait();
			
		}	
	}
	
	@FXML
	public void ajouterNotionAction(ActionEvent e) {
		
		if(!titreNotion.getText().equals("")){
			Notion n = new Notion(titreNotion.getText());
			if(formation.add_notion(n)) {
				tableView.getItems().add(n);
			}
			else {
				Alert alert= new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erreur");
				alert.setContentText("Une notion avec le meme titre existe déjà");
				alert.showAndWait();
				
			}
		}
			
	}
	@FXML
	public void ajouterNotionMouse(MouseEvent e) {
		
		if(!titreNotion.getText().equals("")){
			Notion n = new Notion(titreNotion.getText());
			if(formation.add_notion(n)) {
				tableView.getItems().add(n);
			}
			else {
				Alert alert= new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Erreur");
				alert.setContentText("Une notion avec le meme titre existe déjà");
				alert.showAndWait();
				
			}
		}
	}
	@FXML
	public void supprimerNotion(ActionEvent e) {
		
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("La formation ne contient aucune notion !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner une notion avant !");
			alert.showAndWait();
		}
		else {
			ObservableList<Notion> notions,lignesSelectionnes;
			notions=tableView.getItems();
			lignesSelectionnes=tableView.getSelectionModel().getSelectedItems();
			
			for(Notion p : lignesSelectionnes) {
				formation.supprimer_notion(p);
				notions.remove(p);
			}
		}
		
	}
	@FXML
	public void afficherNotion(ActionEvent e) throws IOException {
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("La formation ne contient aucune notion !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner une notion avant !");
			alert.showAndWait();
		}
		else {
			 FXMLLoader loader = new FXMLLoader();	 
			 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/VisualiserNotion.fxml"));
			 Node home = loader.load();
	         VisualiserNotionController controller = loader.getController();	   
	         controller.setNotion(tableView.getSelectionModel().getSelectedItem());
	         setNode(home);
		}
		
		
	}
	
	@FXML
    public void modifierFormation(ActionEvent e) throws IOException {
		
		 FXMLLoader loader = new FXMLLoader();	 
		 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/AjoutModifFormation.fxml"));
		 Node home = loader.load();
         AjoutModifFormationController controller = loader.getController();	   
         controller.setType("modification");
         setNode(home);
		
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
}
