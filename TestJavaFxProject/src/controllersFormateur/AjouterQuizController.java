package controllersFormateur;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import noyau.Notion;
import noyau.QuizFormateur;

public class AjouterQuizController implements Initializable {

	private QuizFormateur quiz;
	private  HashMap<Notion,Integer> listeNotions = new HashMap<Notion,Integer>();
	private Notion notionSelectionne;
	
	@FXML private ComboBox<Notion> comboBox;
	@FXML private AnchorPane panelStruct;
	@FXML private TextField nbQuestions;
	@FXML private TextField nomQuiz;
	@FXML private DatePicker dateOuverture;
	@FXML private DatePicker dateExpiration;
	@FXML private Button btnGenerer;
	@FXML private Button btnAjouterNotion;
	
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// forcer le champ nombre de question à etre numerique
		nbQuestions.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	nbQuestions.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		comboBox.setItems(getNotions());
		ObservableList<Notion> notions=getNotions();
		if (notions.size()==0) {
			btnGenerer.setDisable(true);
			btnAjouterNotion.setDisable(true);
		    
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
	@FXML
	 public void comboBoxWasUpdated(ActionEvent e)
	 {
		 notionSelectionne= comboBox.getValue();
	 }
	
	
	@FXML
	 private void ajouterNotion(ActionEvent e) throws IOException {
		
		Alert alert =new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Erreur");
		
		if(comboBox.getValue()==null || nbQuestions.getText().equals("") ) {		 
			    alert.setContentText("L'un des champs est vide, remplissez le avant de réeesayer!");
			    alert.showAndWait();
		}
		else if(Integer.parseInt(nbQuestions.getText())==0) {
			 alert.setContentText("Nombre de questions invalide");
			 alert.showAndWait();
		}
		else if(comboBox.getValue().getNbQuestions() < Integer.parseInt(nbQuestions.getText())) {
			 alert.setAlertType(AlertType.WARNING);
			  alert.setTitle("Warning");
			 alert.setContentText("Cette notion ne contient que "+comboBox.getValue().getQuest().size()+",\nLe quiz ne contiendra donc que "+comboBox.getValue().getQuest().size()+"questions de cette notion");
			 alert.showAndWait();
		}
		else if(listeNotions.containsKey(notionSelectionne)) {
			    alert.setAlertType(AlertType.CONFIRMATION);
			    alert.setTitle("Confirmation");
			    alert.setContentText("Cette notion a déjà été choisie si vous cliquez sur ok le nombre de questions pour cette dernière sera modifié");
			    Optional<ButtonType> result = alert.showAndWait();
		    	
		    	if (result.get() == ButtonType.OK){
		    		listeNotions.remove(notionSelectionne);
		    		listeNotions.put(notionSelectionne, Integer.parseInt(nbQuestions.getText()));
		    		new Global().afficherNotif("Notion modifiée avec succès");  
		    	}
			
		}
		else {
			listeNotions.put( notionSelectionne, Integer.parseInt(nbQuestions.getText()));
			new Global().afficherNotif("Notion ajoutée avec succès");  
		}
		 
	 }
	
	 @FXML
	 private void genererQuiz(ActionEvent e) throws IOException {
		 
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
		 else if(listeNotions.size()==0) {
			 alert.setContentText("Vous n'avez choisi aucune notion pour le quiz!");
			 alert.showAndWait();
		 }
		 
		 else {
			 quiz=Global.getFormateur().get_formation().creerQuiz(nomQuiz.getText(),dateOuverture.getValue(), dateExpiration.getValue(), listeNotions);	 
			 
			 if(Global.getFormateur().get_formation().add_quiz(quiz)) {
				 if(quiz.estOuvert() && !quiz.estExpire()) {
					 Global.getFormateur().copieQuizTousApprenants(quiz);
				 }
				 new Global().afficherNotif("Quiz généré avec succès");  
			 }
			 else {
				 alert.setContentText("Vous avez deja créé un quiz avec le même titre !");
				 alert.showAndWait();
			 }
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
