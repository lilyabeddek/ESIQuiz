package controllersApprenant;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import noyau.QC_UM;
import noyau.Qapprenant;
import noyau.Question;
import noyau.Reponse;

public class QuestionTransitionController implements Initializable {
    @FXML
    private AnchorPane homepaneQuestion;
    @FXML
    private Label enonce;
    @FXML
    private ListView<String> propositions;
    @FXML
    private TextArea txtproposition;
    @FXML
    private Button suivant;
    @FXML
    private Label nota;
    @FXML
    private Label reprecedente;
    
    AnchorPane home;
    boolean repondu;
    boolean fin=false;
    
    Qapprenant quizselectionne=MesQuizsController.getInstance().selectedQuiz;
    Iterator<Map.Entry<Question,Reponse>> it = quizselectionne.getReponses().entrySet().iterator();
    Map.Entry<Question,Reponse> e;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AfficherQuestion();
    }
    
    
    
    @FXML  
    public void suivant(ActionEvent event){
    	
       float nbQuestions= quizselectionne.getNbQuestions();
       
       if(!fin) {
         if ((e.getKey().getClass().getName().equals("noyau.QCM")) || (e.getKey().getClass().getName().equals("noyau.QCU"))) {
             if (propositions.getSelectionModel().getSelectedItems().size() != 0) {
                 e.getValue().setReponsesChoisies(propositions.getSelectionModel().getSelectedItems());
                 quizselectionne.Sauv_rep(e.getValue(), e.getKey());
                 if (!repondu) {
                	
                     quizselectionne.setP_accomplissement(quizselectionne.getValeurCompleteAccomplssement() + 100/ nbQuestions );
                     
                 }
             }
         } else {
             if (!txtproposition.getText().equals("")) {
                 e.getValue().setReponseChoisie(txtproposition.getText());

             }
             if (!repondu) {
                 quizselectionne.setP_accomplissement(quizselectionne.getValeurCompleteAccomplssement() + 100/ nbQuestions);
                 System.out.println(quizselectionne.getP_accomplissement());
             }
         }
      }

       if(suivant.getText().equals("Terminer")){
           
           Alert a =new Alert(Alert.AlertType.INFORMATION);
           a.setContentText("Felicitations ! quiz terminé , vous pouvez le soumettre à évaluation ou le reprendre à nouveau");
           a.showAndWait();
           quizselectionne=null;
           ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/MesQuizs.fxml");
       }else{ AfficherQuestion(); }
    }
    
    
    public void AfficherQuestion() {
    	
        if (it.hasNext()) {

            e = it.next();
            if(!it.hasNext()) {suivant.setText("Terminer"); }
            
            enonce.setText(e.getKey().getEnonce());
            if(e.getValue().getReponsesSChoisies().size()>0){repondu=true;}else {repondu=false;}
            if ((e.getKey().getClass().getName().equals("noyau.QCM")) || (e.getKey().getClass().getName().equals("noyau.QCU"))) {
                propositions.setVisible(true);
                txtproposition.setVisible(false);
                propositions.getItems().clear();
                propositions.getItems().addAll(e.getKey().getPropositionsCorrectes());
                propositions.getItems().addAll(((QC_UM) e.getKey()).getPropositionsFausses());
                if (e.getKey().getClass().getName().equals("noyau.QCM")) {
                    propositions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                } else {
                    propositions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            }else {
                propositions.setVisible(false);
                txtproposition.clear();
                txtproposition.setVisible(true);
            }
            if(e.getValue().getReponsesSChoisies().size()>0){

                reprecedente.setText("Réponses choisies : "+e.getValue().getReponsesSChoisies().toString());
                nota.setText("Note : Si vous voulez garder les mêmes réponses cliquez sur Suivant/Teminer");
            }else {
                reprecedente.setText("");
                nota.setText("");
            }
        }
        else {
        	fin=true;
        }
    }

}
