package controllersFormateur;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Notion;
import noyau.QO;

public class CreerModifQoController {
	
	private Notion notion;
	private QO question;
	
	@FXML private TableView<String> tableView;
	@FXML private TableColumn<String,String> propJustesColumn;

	@FXML private AnchorPane holdPane;
	@FXML private TextField enonce;
	@FXML private Label enonceText;
	@FXML private Button BtnEnregistrer;
	@FXML private TextField propVraie;
	@FXML private Label titrePage;
	
	@FXML private Button btnAjoutPropJuste;
	@FXML private Button btnSuppPropJuste;
	
	@FXML private Label lblAjoutPropJuste;

	
	public void setNotion(Notion notion,QO question,String type) {
		this.notion=notion;
		propJustesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		if (type.equals("modification")) {
			
			this.question=question;
			tableView.setItems(getPropCorrectes());
			
			titrePage.setText("Modification d'une question ouverte");
			enonce.setVisible(false);
			enonceText.setVisible(true);
			enonceText.setText(question.getEnonce());
			BtnEnregistrer.setVisible(false);
		}
		else {
			tableView.setDisable(true);
			propVraie.setDisable(true);
			btnAjoutPropJuste.setDisable(true);
			btnSuppPropJuste.setDisable(true);
			lblAjoutPropJuste.setDisable(true);

			
		}
	
	}
	
	 private ObservableList<String> getPropCorrectes() {
			ObservableList<String> propsJustes = FXCollections.observableArrayList(question.getPropositionsCorrectes());
			return propsJustes;
	 }
	
	@FXML
	private void enregister(ActionEvent e) throws IOException {
		if(!BtnEnregistrer.getText().equals("Modifier")) {
			
			if(enonce.getText().equals("")) {
				 Alert alert= new Alert(AlertType.ERROR);
			     alert.setHeaderText(null);
				 alert.setTitle("Erreur");
				 alert.setContentText("Remplissez le champ enoncé avant !");
				 alert.showAndWait();
			}else {
				 question=new QO(enonce.getText());
				    if ( notion.ajouterQuestion(question)) {
				    	tableView.setDisable(false);
						propVraie.setDisable(false);
						btnAjoutPropJuste.setDisable(false);
						btnSuppPropJuste.setDisable(false);
						lblAjoutPropJuste.setDisable(false);
						BtnEnregistrer.setText("Modifier"); 
						new Global().afficherNotif("Question enregistrée avec succès");
				    }
				    else {
				    	 Alert alert= new Alert(AlertType.ERROR);
					     alert.setHeaderText(null);
						 alert.setTitle("Erreur");
						 alert.setContentText("Une question avec le meme énoncé existe déjà !");
						 alert.showAndWait();
				    }
			}
			
			   
			   			       
		}
		else {
			if(enonce.getText().equals("")) {
				 Alert alert= new Alert(AlertType.ERROR);
			     alert.setHeaderText(null);
				 alert.setTitle("Erreur");
				 alert.setContentText("Remplissez le champ enoncé avant !");
				 alert.showAndWait();
			}
			else if (notion.rechEnonceQuestion(enonce.getText())) {
				 
				 Alert alert= new Alert(AlertType.ERROR);
			     alert.setHeaderText(null);
				 alert.setTitle("Erreur");
				 alert.setContentText("Cette notion contient déjà une question avec le meme énoncé!");
				 alert.showAndWait();
				 
			 }
			 else {
				 question.setEnonce(enonce.getText());
				 new Global().afficherNotif("enoncé de la question modifié avec succès");  
			 }
		}
		  
	}
	@FXML
	private void ajouterPropVraieAction(ActionEvent e) {
		if(question.setPropositionCorrecte(propVraie.getText())) {
			tableView.getItems().add(propVraie.getText());
		}
		else {
			 Alert alert= new Alert(AlertType.ERROR);
		     alert.setHeaderText(null);
			 alert.setTitle("Erreur");
			 alert.setContentText("Cette question contient déjà cette proposition!");
			 alert.showAndWait();
		}
			
	}
	@FXML
	public void ajouterPropVraieMouse(MouseEvent e) {
		if(question.setPropositionCorrecte(propVraie.getText())) {
			tableView.getItems().add(propVraie.getText());
		}
		else {
			 Alert alert= new Alert(AlertType.ERROR);
		     alert.setHeaderText(null);
			 alert.setTitle("Erreur");
			 alert.setContentText("Cette question contient déjà cette proposition!");
			 alert.showAndWait();
		}
	}
	@FXML
	private void supprimerPropVraie(ActionEvent e) {

		ObservableList<String> propositionsJustes,lignesSelectionnes;
		propositionsJustes=tableView.getItems();
		lignesSelectionnes=tableView.getSelectionModel().getSelectedItems();
		
		for(String s : lignesSelectionnes) {
			question.RemovePropositionCorrecte(s);
			propositionsJustes.remove(s);
		}
	}
	
	@FXML
    private void revenirArriere(ActionEvent e) throws IOException{
		if (question!=null) {
			if(question.getPropositionsCorrectes().size()<1) {
				 Alert alert= new Alert(AlertType.ERROR);
			     alert.setHeaderText(null);
				 alert.setTitle("Erreur");
				 alert.setContentText("Une question Qcm doit au moins contenir une proposition juste!");
				 alert.showAndWait();
			}
			else {
				 FXMLLoader loader = new FXMLLoader();	 
				 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/VisualiserNotion.fxml"));
				 Node home = loader.load();
		         VisualiserNotionController controller = loader.getController();	   
		         controller.setNotion(notion);
		         setNode(home);
			}
		}
		else {
			 FXMLLoader loader = new FXMLLoader();	 
			 loader.setLocation(getClass().getResource("/fxmlFilesFormateur/VisualiserNotion.fxml"));
			 Node home = loader.load();
	         VisualiserNotionController controller = loader.getController();	   
	         controller.setNotion(notion);
	         setNode(home);
		}
		
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
