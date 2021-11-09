package controllersApprenant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import noyau.Qapprenant;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controllersFormateur.Global;

public class MesQuizsController implements Initializable {
    
	@FXML private TableView<Qapprenant> table;
	@FXML private Button commancer;
	@FXML private Button soumettre;
	@FXML private Button correction;
	@FXML private TableColumn<Qapprenant,String> titre ;
	@FXML private TableColumn<Qapprenant, LocalDate> dateDeb;
	@FXML private TableColumn<Qapprenant,LocalDate> dateFin;
	@FXML private TableColumn<Qapprenant,Double> p_accomplissement;
	@FXML private TableColumn<Qapprenant,Double> p_reussite;
	
	
    AnchorPane home;
    Alert a=new Alert(Alert.AlertType.WARNING);

    public Qapprenant selectedQuiz;
    public static MesQuizsController instance;
    public MesQuizsController(){
        instance=this;
    }
    public static MesQuizsController getInstance(){
        return instance;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        titre.setCellValueFactory(new PropertyValueFactory<Qapprenant,String>("nom"));
        dateDeb.setCellValueFactory(new PropertyValueFactory<Qapprenant, LocalDate>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<Qapprenant, LocalDate>("dateFin"));
        p_accomplissement.setCellValueFactory(new PropertyValueFactory<Qapprenant,Double>("p_accomplissement"));
        p_reussite.setCellValueFactory(new PropertyValueFactory<Qapprenant,Double>("p_reussite"));

        table.setItems(getQuizs());
        table.setOnMouseClicked((MouseEvent event)->{
        	
            if (event.getClickCount()>0){
               selectedQuiz=table.getSelectionModel().getSelectedItem();
               System.out.println(selectedQuiz.estSoumi());
               System.out.println(selectedQuiz.estExpire());
               System.out.println(selectedQuiz.estAccimpli());
               if(selectedQuiz.estSoumi() || selectedQuiz.estExpire()) {
            	   correction.setDisable(false);
            	   soumettre.setDisable(true);
            	   commancer.setDisable(true);
               }
               else {
            	   if(selectedQuiz.estAccimpli()) {
            		   correction.setDisable(true);
                	   soumettre.setDisable(false);
                	   commancer.setDisable(false);
            	   }
            	   else {
            		   correction.setDisable(true);
                	   soumettre.setDisable(true);
                	   commancer.setDisable(false);
            	   }
            	   
               } 
            }
        });
    }

    
    @FXML void afficherCorrection() {
    	ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/AfficherReponsesQuiz.fxml");
    }
    @FXML
    public void commancerQuiz(ActionEvent event){
    	
        a = new Alert(Alert.AlertType.WARNING);
        if(selectedQuiz != null  ){
            if(selectedQuiz.getP_accomplissement()!=100){
              if (selectedQuiz.getDateFin().isBefore(LocalDate.now())) {
                  a.setContentText(" Quiz expiré depuis le : "+selectedQuiz.getDateFin());
                  a.showAndWait();
              }else {
                      ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/QuestionTransition.fxml");}
              }
            else {
                if(selectedQuiz.estSoumi()) {
                    a.setContentText(" Quiz déja remis impossible de l'entammer");
                    a.showAndWait();
                }
                else {
                    ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/QuestionTransition.fxml");
                }
            }
        }
    }
    @FXML
    public void soumettreQuiz(ActionEvent event){
        if(selectedQuiz != null){

            if (selectedQuiz.getDateFin().isBefore(LocalDate.now())) {
                a.setContentText(" Quiz expiré depuis le : "+selectedQuiz.getDateFin());
                a.showAndWait();
            }else {

                if (selectedQuiz.getP_accomplissement() >= 100.0) {
                    if (!selectedQuiz.estSoumi()) {
                        // evaluer le quiz
                        selectedQuiz.evaluerQuiz();
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setContentText(" Quiz évalué avec Succès  !");
                        a.showAndWait();
                      
                        table.getItems().clear();
                        table.setItems(getQuizs());
                        // ApprenantHomeController.getInstance().createPage("/fxmlFilesApprenant/AfficherReponsesQuiz.fxml");
                                        
                    } else {
                        a.setContentText(" Quiz déja évalué , reévaluation impossible");
                        a.showAndWait();
                    }
                } else {
                    a.setContentText(" Quiz non Accomplie , impossible de le soumettre");
                    a.show();

                }
            }
        }
    }
    @FXML
    public ObservableList<Qapprenant> getQuizs(){
        ObservableList<Qapprenant> quizs=FXCollections.observableArrayList();
        quizs.addAll(Global.getApprenant().getQuizs());
        //list.getSelectionModel().szs=new FXCollections().obetSelectionMode(SelectionMode.MULTIPLE);
        //list.getItems().addAll();
        return quizs;
    }
}
