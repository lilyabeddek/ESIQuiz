package controllersFormateur;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.QuizFormateur;

public class ListQuizsController implements Initializable {
	
	private Set<QuizFormateur> quizsFormateur;
	
	@FXML AnchorPane panelStruct;
	@FXML private TableView<QuizFormateur> tableView;
	@FXML private TableColumn<QuizFormateur,String> nomQuiz;
	@FXML private TableColumn<QuizFormateur,LocalDate> dateOuverture;
	@FXML private TableColumn<QuizFormateur,LocalDate> dateExpiration;
	@FXML private TableColumn<QuizFormateur,Integer> nbQuestions;
	
	
	@FXML private Button creerQuizButton;
	@FXML private Button modifierQuizButton;
	@FXML private Button supprimerQuizButton;
	@FXML private Button visualiserQuizButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		 nomQuiz.setCellValueFactory(new PropertyValueFactory<QuizFormateur,String>("nom"));
		 nbQuestions.setCellValueFactory(new PropertyValueFactory<QuizFormateur,Integer>("nbQuestions"));
	     dateOuverture.setCellValueFactory(new PropertyValueFactory<QuizFormateur,LocalDate>("dateDebut"));
	     dateExpiration.setCellValueFactory(new PropertyValueFactory<QuizFormateur,LocalDate>("dateFin"));
	     tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	     try {
				quizsFormateur=Global.getFormateur().get_formation().getQuizs();
				tableView.setItems(getQuizs());
		 }
		 catch(NullPointerException e) {
			 
			 creerQuizButton.setDisable(true);
			 modifierQuizButton.setDisable(true);
			 supprimerQuizButton.setDisable(true);
			 visualiserQuizButton.setDisable(true);
			 
			 Alert alert= new Alert(AlertType.WARNING);
			 alert.setTitle("Attention");
			 alert.setHeaderText(null);
			 alert.setContentText("Vous devez d'abord créer une formation pour pouvoir creer des quizs");
			 alert.showAndWait();
		 }
				
		 
		
	}
	
	private ObservableList<QuizFormateur> getQuizs() {
		ObservableList<QuizFormateur> quizs = FXCollections.observableArrayList(this.quizsFormateur);
		return quizs;
	}
	
	@FXML 
	private void visualiserQuiz(ActionEvent e) throws IOException {	
		if(tableView.getItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("La formation ne contient aucun quiz !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Erreur");
			alert.setContentText("Veuillez selectionner un quiz avant !");
			alert.showAndWait();
		}
		else {
			FXMLLoader loader = new FXMLLoader();	 
			loader.setLocation(getClass().getResource("/fxmlFilesFormateur/AfficherQuiz.fxml"));
			Node home = loader.load();
	        AfficherQuizController controller = loader.getController();	   
	        controller.initQuiz(tableView.getSelectionModel().getSelectedItem());
	        setNode(home);
		}
		
	
	}
	@FXML 
	private void supprimerQuiz(ActionEvent e) throws IOException {	
		Alert alert= new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Erreur");
		
		if(tableView.getItems().size()==0) {
		
			alert.setContentText("La formation ne contient aucun quiz !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
		
			alert.setContentText("Veuillez selectionner un quiz avant !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItem().estOuvert()) {
	    	alert.setContentText("Ce quiz est déjà ouvert vous ne pouvez donc plus le supprimer!");
			alert.showAndWait();
	    }
		else {

			ObservableList<QuizFormateur> quizs,lignesSelectionnes;
			quizs=tableView.getItems();
			lignesSelectionnes=tableView.getSelectionModel().getSelectedItems();
			
			for(QuizFormateur q : lignesSelectionnes) {
				this.quizsFormateur.remove(q);
				quizs.remove(q);
			}
		}
		
	}
	
	@FXML 
	private void creerQuiz(ActionEvent e) throws IOException {
		createPage("/fxmlFilesFormateur/AjouterQuiz.fxml");
		 
	}
	
	@FXML 
	private void modifierQuiz(ActionEvent e) throws IOException {
		Alert alert= new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Erreur");
		
		if(tableView.getItems().size()==0) {
			
			alert.setContentText("La formation ne contient aucun quiz !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItems().size()==0) {
	
			alert.setContentText("Veuillez selectionner un quiz avant !");
			alert.showAndWait();
		}
	    else if (tableView.getSelectionModel().getSelectedItem().estOuvert()) {
	    	alert.setContentText("Ce quiz est déjà ouvert vous ne pouvez donc plus le modifier!");
			alert.showAndWait();
	    }
		else {
			
			FXMLLoader loader = new FXMLLoader();	 
			loader.setLocation(getClass().getResource("/fxmlFilesFormateur/ModifierInfoQuiz.fxml"));
			Node home = loader.load();
	        ModifierInfoQuizController controller = loader.getController();	   
	        controller.initQuiz(tableView.getSelectionModel().getSelectedItem());
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
			Node home= FXMLLoader.load(getClass().getResource(nomFichier));
			setNode(home);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
