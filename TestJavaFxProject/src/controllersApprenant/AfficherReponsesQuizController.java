package controllersApprenant;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import controllersFormateur.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import noyau.Qapprenant;
import noyau.Question;
import noyau.Reponse;

public class AfficherReponsesQuizController implements Initializable{
	
	    @FXML
	    private AnchorPane homepaneQuestion;
	    @FXML
	    private Label nom;
	    @FXML
	    private Label enonce;
	    @FXML
	    private ListView<String> propositions;
	    @FXML
	    private TextArea txtproposition;
	    @FXML
	    private Button suivant;
	    @FXML
	    private Label pReussiteQuest;
	    @FXML
	    private Label pReussiteQuiz;
	    @FXML
	    private Label reprecedente;
	    
	    AnchorPane home;
	    boolean fin=false;
	    
	    Qapprenant quizselectionne=MesQuizsController.getInstance().selectedQuiz;
	    Iterator<Map.Entry<Question,Reponse>> it = quizselectionne.getReponses().entrySet().iterator();
	    Map.Entry<Question,Reponse> e;
	    
	    @Override
	    public void initialize(URL url, ResourceBundle resourceBundle) {
	    	nom.setText(quizselectionne.getNom());
	    	pReussiteQuiz.setText(Double.toString(quizselectionne.getP_reussite()));
	        AfficherQuestion();
	    }
	    
	    
	    @FXML 
	    public void revenirArriere() {
	    	ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/MesQuizs.fxml");
	    }
	    
	    @FXML  
	    public void suivant(ActionEvent event){
	    
	       if(suivant.getText().equals("Terminer")){
	    	   Global.getApprenant().remplacer_quiz( Global.getApprenant().rech_quiz(MesQuizsController.getInstance().selectedQuiz),quizselectionne);
	           Alert a =new Alert(Alert.AlertType.INFORMATION);
	           a.setContentText("Fin du quiz");
	           a.showAndWait();
	           quizselectionne=null;
	           ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/MesQuizs.fxml");
	       }
	       else{AfficherQuestion();}
	    }
	    
	    
	    public void AfficherQuestion() {
	    	
	        if (it.hasNext()) {

	        	
	        	propositions.getItems().clear();
	            e = it.next();
	            if (!it.hasNext()) {suivant.setText("Terminer");}
	            enonce.setText(e.getKey().getEnonce());
	            propositions.getItems().addAll(e.getKey().getPropositionsCorrectes());
	            reprecedente.setText(e.getValue().getReponsesSChoisies().toString());
	            pReussiteQuest.setText(Double.toString(e.getValue().getpObtenu()));
	            
	        }else {
	            
	            fin=true;
	        }
	    }

	
	
	
	
	

}
