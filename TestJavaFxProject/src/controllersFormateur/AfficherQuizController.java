package controllersFormateur;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import noyau.QCM;
import noyau.QCU;
import noyau.Question;
import noyau.QuizFormateur;

public class AfficherQuizController {
	
	private QuizFormateur quiz;
	@FXML private ListView<Question> listView;
	@FXML private VBox boxPropositions;
	@FXML private AnchorPane panelStruct;
	@FXML private Label nom;
	@FXML private Label dateOuverture;
	@FXML private Label dateExpiration;	
	@FXML private Label question;
	
	@FXML private Button btnSupprimerQuest;
	@FXML private Button btnAjouterQuest;
	@FXML private Button btnRemplacerQuest;
	
	
	
	public void initQuiz(QuizFormateur quizFormateur) { 
		 nom.setWrapText(true);
		 question.setWrapText(true);
		 
		 this.quiz=quizFormateur;
		 nom.setText(quizFormateur.getNom());
		 dateOuverture.setText(quizFormateur.getDateDebut().toString());
		 dateExpiration.setText(quizFormateur.getDateFin().toString());
		 
		 ObservableList<Question> questions = FXCollections.observableArrayList(quiz.getQuestions());
		 listView.setItems(questions);
		 
		 if(quiz.estOuvert()) {
			 btnSupprimerQuest.setDisable(true);
			 btnAjouterQuest.setDisable(true);
			 btnRemplacerQuest.setDisable(true);
		 }

	 }
	 @FXML private void handleMouseClick(MouseEvent arg0) {

	    question.setText(listView.getSelectionModel().getSelectedItem().getEnonce());
	    boxPropositions.getChildren().clear();
	    
	    if (listView.getSelectionModel().getSelectedItem() instanceof QCU) {
	    	QCU q = (QCU)listView.getSelectionModel().getSelectedItem();
	    	RadioButton radio;
	        ToggleGroup group = new ToggleGroup();
	    	for(String s : q.getPropositionsAleatoires()) {
	    		radio= new RadioButton(s);
	    		radio.setWrapText(true);
	    		radio.setFont(new Font(16));
	    		radio.setPadding( new Insets(15,0,15,0));
	    		radio.setToggleGroup(group);
	    		boxPropositions.getChildren().add(radio);
	    	}
	    }
	    else if(listView.getSelectionModel().getSelectedItem() instanceof QCM){
	    	QCM q = (QCM)listView.getSelectionModel().getSelectedItem();
	    	CheckBox checkBox;
	    	for(String s : q.getPropositionsAleatoires()) {
	    		checkBox= new CheckBox(s);
	    		checkBox.setWrapText(true);
	    		checkBox.setFont(new Font(16));
	    		checkBox.setPadding( new Insets(15,0,15,0));
	    		boxPropositions.getChildren().add(checkBox);
	    	}
	    }
	    else {
	    	Label titre= new Label("Saisissez votre réponse :");
	    	titre.setFont(new Font(18));
	    	titre.setPadding(new Insets(15,0,15,0));
	    	
	    	TextArea reponse=new TextArea();
	    	reponse.setPromptText("Espace de saisie de la réponse de l'apprenant");
	    	reponse.setPrefHeight(92);  
	    	boxPropositions.getChildren().add(titre);
	    	boxPropositions.getChildren().add(reponse);
	    	
	    }
	 }
	 
	 @FXML private void supprimerQuestion(ActionEvent arg0) throws IOException  {	
		  Alert alert= new Alert(AlertType.ERROR);
          alert.setHeaderText(null);
	      alert.setTitle("Erreur");
	      
		 if(listView.getItems().size()==0) {
			  alert.setContentText("Ce quiz ne contient aucune question!");
		      alert.showAndWait();
		 }
	     else if (listView.getSelectionModel().getSelectedItems().size()==0) {
	    	 alert.setContentText("Veuillez selectionner une question d'abord !");
		     alert.showAndWait();
	     }
		 else {
			 
			if( quiz.supprimerQuestion(listView.getSelectionModel().getSelectedItem())) {
				listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
				question.setText("");
				boxPropositions.getChildren().clear();
				new Global().afficherNotif("La question selectionnée a été supprimé avec succès");
			}
		 }
           
     }
	 @FXML private void remplacerQuestion(ActionEvent arg0) throws IOException {	
		 
		 Alert alert= new Alert(AlertType.ERROR);
         alert.setHeaderText(null);
	     alert.setTitle("Erreur");
	      
		 if(listView.getItems().size()==0) {
			  alert.setContentText("Ce quiz ne contient aucune question!");
		      alert.showAndWait();
		 }
	     else if (listView.getSelectionModel().getSelectedItems().size()==0) {
	    	 alert.setContentText("Veuillez selectionner une question d'abord !");
		     alert.showAndWait();
	     }
		 else {
 
			 if( quiz.ajouQuestionMemeNotion(listView.getSelectionModel().getSelectedItem(), Global.getFormateur().get_formation().getNotions())) {
				  
					question.setText("");
					boxPropositions.getChildren().clear();
					quiz.supprimerQuestion(listView.getSelectionModel().getSelectedItem());
					
					listView.getItems().clear();
					ObservableList<Question> questions = FXCollections.observableArrayList(quiz.getQuestions());
					listView.setItems(questions);
					
					new Global().afficherNotif("La question selectionnée a été remplacé avec succès");
			 } 
			 else {
				 
				 alert.setContentText("Remplacement de la question impossible en raison de la suppression de la notion ou de la non-disponibilité de questions en plus dans la même notion!");
			     alert.showAndWait();
			 }
		 }
		 
		
     }
     @FXML private void ajouterQuestion(ActionEvent arg0) {		   
         
    	 
    	 try {
    		 FXMLLoader loader = new FXMLLoader();
             loader.setLocation(getClass().getResource("/fxmlFilesFormateur/AjoutQuestQuiz.fxml"));
             Parent menuParnet = loader.load();
             
             Scene scene= new Scene(menuParnet, 556, 328);
             AjoutQuestQuizController controller= loader.getController();
             controller.initQuiz(this.quiz);
             
             Stage stage = new Stage();
             stage.setTitle("Ajouter une Question au Quiz");
             stage.initStyle(StageStyle.UNDECORATED);
             stage.setScene(scene);
             stage.showAndWait();
             
             listView.getItems().clear();
			 ObservableList<Question> questions = FXCollections.observableArrayList(quiz.getQuestions());
			 listView.setItems(questions);
            
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }
	
	
	
	
	
	
	
	@FXML
	private void revenirEnArriere(ActionEvent event) {
		createPage("/fxmlFilesFormateur/ListQuizs.fxml");
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
