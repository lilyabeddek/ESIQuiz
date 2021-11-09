package controllersFormateur;

import java.io.IOException;
import java.time.LocalDate;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.QuizFormateur;

public class ModifierInfoQuizController  {

	private QuizFormateur quiz;
	
	@FXML private AnchorPane panelStruct;
	@FXML private TextField nomQuiz;
	@FXML private DatePicker dateOuverture;
	@FXML private DatePicker dateExpiration;
	@FXML private Button btnEnregistrer;

	 public void initQuiz(QuizFormateur quizFormateur) { 
		 this.quiz=quizFormateur;
		 nomQuiz.setText(quiz.getNom());
	     dateOuverture.setValue(quiz.getDateDebut());
		 dateExpiration.setValue(quiz.getDateFin());
	 }
	
	 @FXML
	 private void enregistrerModif(ActionEvent e) throws IOException {
		 
		 Alert alert =new Alert(AlertType.ERROR);
		 alert.setHeaderText(null);
		 alert.setTitle("Erreur");
		   
		 if(nomQuiz.getText().equals("") || dateOuverture.getValue()==null || dateExpiration.getValue()==null ) {
			 alert.setContentText("L'un des champs est vide, remplissez le avant de réeesayer!");
			 alert.showAndWait();
		 }
		 else if(dateOuverture.getValue().compareTo(dateExpiration.getValue())>0) {
			 alert.setContentText("La date d'ouverture du quiz est érronée par rapport à sa date d'expiration!");
			 alert.showAndWait();
		 }
		 else if (dateOuverture.getValue().compareTo(Global.getFormateur().get_formation().get_datedeb())<0) {
			 alert.setContentText("La date d'ouverture du quiz est érronée par rapport à la date debut de la formation!");
			 alert.showAndWait();
		 }
		 else if (dateExpiration.getValue().compareTo(Global.getFormateur().get_formation().get_datefin())>0) {
			 alert.setContentText("La date d'expiration du quiz est érronée par rapport à la date fin de la formation!");
			 alert.showAndWait();
		 }
		 else if (dateOuverture.getValue().compareTo(LocalDate.now())<0) {
			 alert.setContentText("La date d'ouverture du quiz érronée par rapport à la date d'aujourd'hui !");
			 alert.showAndWait();
		 }
		 else {
			 quiz.setNom(nomQuiz.getText());	
			 quiz.setDateDebut(dateOuverture.getValue());
			 quiz.setDateFin(dateExpiration.getValue());
			 new Global().afficherNotif("Quiz modifié avec succès");  
		 }
		 
		
		
	 }
	
	
	@FXML 
	private void revenirArriere(ActionEvent e) {
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
