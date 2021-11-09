package controllersFormateur;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Notion;
import noyau.QCM;
import noyau.QCU;
import noyau.QO;
import noyau.Question;

public class VisualiserNotionController {
	
	private Notion notion;
	
	@FXML private TableView<Question> tableView;
	@FXML private TableColumn<Question,String> enonceColumn;
	@FXML private AnchorPane holdPane;
	@FXML private Label titreNotion;
	
	@FXML private Button btnAddQo;
	@FXML private Button btnAddQcu;
	@FXML private Button btnAddQcm;
	@FXML private Button btnSupprimer;
	

	public void setNotion(Notion notion) {
		this.notion=notion;
		titreNotion.setText(notion.getTitre());
		enonceColumn.setCellValueFactory(new PropertyValueFactory<Question,String>("enonce"));
		tableView.setItems(getNotions());	
		
		if (Global.getFormateur().get_formation().terminee()) {
			btnAddQo.setDisable(true);
			btnAddQcu.setDisable(true);
			btnAddQcm.setDisable(true);
			btnSupprimer.setDisable(true);
		}
		else {

			tableView.setEditable(true);
			enonceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		}
		
	}
	

	 private ObservableList<Question> getNotions() {
		ObservableList<Question> questions = FXCollections.observableArrayList(notion.getQuest());
		return questions;
	 }
	
	 @FXML
     public void changerEnonceQuestCell(CellEditEvent<Question,String> e) {
		
		 if (notion.rechEnonceQuestion(e.getNewValue().toString())) {
			 
			 Alert alert= new Alert(AlertType.ERROR);
		     alert.setHeaderText(null);
			 alert.setTitle("Erreur");
			 alert.setContentText("Cette notion contient déjà une question avec le meme énoncé!");
			 alert.showAndWait();
			 
		 }
		 else {
			 Question q= tableView.getSelectionModel().getSelectedItem();
			 q.setEnonce(e.getNewValue().toString());
		 }
	    
     }
	
	@FXML
	 public void ajouterQcu(ActionEvent e) throws IOException{
		
		 FXMLLoader loader = new FXMLLoader();	 
		 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/CreerModifQcu.fxml"));
		 Node home = loader.load();
		 CreerModifQcuController controller = loader.getController();	   
         controller.setNotion(notion,null,"creation");
         setNode(home);
		
	}
	@FXML
	 public void ajouterQcm(ActionEvent e) throws IOException{
		
		 FXMLLoader loader = new FXMLLoader();	 
		 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/CreerModifQcm.fxml"));
		 Node home = loader.load();
		 CreerModifQcmController controller = loader.getController();	   
         controller.setNotion(notion,null,"creation");
         setNode(home);
		
	}
	@FXML
	 public void ajouterQo(ActionEvent e) throws IOException{
		
		 FXMLLoader loader = new FXMLLoader();	 
		 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/CreerModifQo.fxml"));
		 Node home = loader.load();
		 CreerModifQoController controller = loader.getController();	   
         controller.setNotion(notion,null,"creation");
         setNode(home);
		
	}
	@FXML
	 public void modifierQuestion(ActionEvent e) throws IOException{
		
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("La notion ne contient aucune question !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner une question avant !");
			alert.showAndWait();
		}
		else {
			FXMLLoader loader = new FXMLLoader();
			Node home;
			switch(tableView.getSelectionModel().getSelectedItem().getClass().toString()){
			
			    case "class noyau.QCM":
			    	
			    	 loader = new FXMLLoader();	 
					 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/CreerModifQcm.fxml"));
					 home = loader.load();
					 CreerModifQcmController controllerQCM = loader.getController();	   
			         controllerQCM.setNotion(notion,(QCM)tableView.getSelectionModel().getSelectedItem(),"modification");
			         setNode(home);
				   break;
				   
			    case "class noyau.QCU":
			    	
			    	 loader = new FXMLLoader();	 
					 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/CreerModifQcu.fxml"));
					 home = loader.load();
					 CreerModifQcuController controllerQCU = loader.getController();	   
			         controllerQCU.setNotion(notion,(QCU)tableView.getSelectionModel().getSelectedItem(),"modification");
			         setNode(home);
				     break;
				   
			    case "class noyau.QO":

			    	 loader = new FXMLLoader();	 
					 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/CreerModifQo.fxml"));
					 home = loader.load();
					 CreerModifQoController controllerQo = loader.getController();	   
			         controllerQo.setNotion(notion,(QO)tableView.getSelectionModel().getSelectedItem(),"modification");
			         setNode(home);
				     break;
				default :
					System.out.println(tableView.getSelectionModel().getSelectedItem().getClass().toString());
					break;
			
			}
			
		}
		
		
		
		
	}
	@FXML
	 public void supprimerQuestion(ActionEvent e){
		
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("La notion ne contient aucune question !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner une question avant !");
			alert.showAndWait();
		}
		else {
			ObservableList<Question> questions,lignesSelectionnes;
			questions=tableView.getItems();
			lignesSelectionnes=tableView.getSelectionModel().getSelectedItems();
			
			for(Question q : lignesSelectionnes) {
				notion.supprimerQuestion(q);
				questions.remove(q);
			}
		}
			
	}
	@FXML
	 public void revenirArriere(ActionEvent e){
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
