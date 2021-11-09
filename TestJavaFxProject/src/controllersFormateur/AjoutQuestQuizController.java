package controllersFormateur;


import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import noyau.Notion;
import noyau.QuizFormateur;

public class AjoutQuestQuizController  {

	private QuizFormateur quiz;
	@FXML private ComboBox<Notion> comboBox;
	@FXML private Button valider;
	
	
	
	public void initQuiz(QuizFormateur quizFormateur) { 
		 this.quiz=quizFormateur;
		 comboBox.setItems(getNotions());
		 
		 ObservableList<Notion> notions=getNotions();
		 if (notions.size()==0) {
				valider.setDisable(true);
			    
				Alert alert =new Alert(AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setTitle("Wraning");
				alert.setContentText("La formation ne contient aucune notion ou bien aucune notion ne contient de question, vous ne pourrez donc pas encore générer de quiz");
			    alert.showAndWait();
			}
		 
		 
		 
		 
	 }
	private ObservableList<Notion> getNotions() {
		ObservableList<Notion> notions = FXCollections.observableArrayList();
		for(Notion n: Global.getFormateur().get_formation().getNotions()) {
			if(n.getNbQuestions()!=0) {notions.add(n);}		
		}	
		return notions;
	}
	
	@FXML private void fermer(MouseEvent e) {
		 Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	     window.close();
	}
	
	@FXML private void Enregistrer(ActionEvent e) throws IOException {
		
		if(quiz.AjouterQuestQuiz(comboBox.getSelectionModel().getSelectedItem())) {
			new Global().afficherNotif("Une question a été ajouté avec succès");
		}		
		else {
			 Alert alert= new Alert(AlertType.ERROR);
	         alert.setHeaderText(null);
		     alert.setTitle("Erreur");
			 alert.setContentText("Ajout impossible, soit toutes les questions de cette notion sont dans le quiz ou que la notion ne contient plus aucue question");
		     alert.showAndWait();
		}
		
		 
		 Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	     window.close();
	}
}
