package controllersFormateur;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import noyau.Formation;

public class MenuFormateurController implements Initializable {
	 
	private double xOffset = 0; 
	private double yOffset = 0;
	
	 @FXML private Label login;
	 @FXML private ImageView profilPicture;
	 @FXML private AnchorPane holdPane;
	 AnchorPane home;
	 

	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	//initialisation du nom d'utilisateur et photo de profil du formateur	
			login.setText(Global.getFormateur().getUsername());
			try {profilPicture.setImage(new Image(Global.getFormateur().getimage()));}
			catch(Exception e) {}		
	}
		
		
	@FXML 
	private void accueil(ActionEvent e) {
		createPage("/fxmlFilesFormateur/Accueil.fxml");
	}
	 @FXML 
	 private void closeButtonAction(MouseEvent e){//fermer l'application
		 //sauvgarde de toutes les modifications faites par le formateur dans le fichier TestSerialization
		 FileOutputStream fos = null;		
	     try {
	    	    new FileOutputStream("TestSerialisation.ser").close();
	            fos = new FileOutputStream("TestSerialisation.ser");
	            ObjectOutputStream oos = new ObjectOutputStream(fos);
	            oos.writeObject(Global.getFormateur());
	            oos.close();
	     } catch (FileNotFoundException exp) {
	            exp.printStackTrace();
	     } catch (IOException exp) {
	            exp.printStackTrace();
	     }
		 Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	     window.close();
	 }
	 
	
	@FXML
	public void paramCompte(ActionEvent e) {//chargement de la fenetre modification des parametres du compte du formateur
		createPage("/fxmlFilesFormateur/ParametresCompte.fxml");	
	}
	
	@FXML
	public void gestionApprenant(ActionEvent e) {//chargement de la fenetre gestion des apprenants
		createPage("/fxmlFilesFormateur/VisualiserApprenants.fxml");		
	}
	
	@FXML
	public void gestionFormation(ActionEvent e) {//chargement de la fenetre gestion de la formation
		
		if(Global.getFormateur().get_formation() instanceof Formation) {// si le formateur a deja cree uen formation
			createPage("/fxmlFilesFormateur/VisualiserFormation.fxml");
		}
		else {// si le formateur n'a pas encore cree de formation
			createPage("/fxmlFilesFormateur/AjoutModifFormation.fxml");
		}
		
		
	}
	@FXML
	public void gestionQuizs(ActionEvent e) {//chargement de la fenetre gestion des quizs de la formation
		createPage("/fxmlFilesFormateur/ListQuizs.fxml");
		
	}
	
	@FXML
	public void seDeconnecter(ActionEvent e) throws IOException {//deconnection du formateur ==revenir à la fenetre du logIn
		
		//sauvgrade des modifications faites par le formateur
		 FileOutputStream fos = null;
	     try {
	            fos = new FileOutputStream("TestSerialisation.ser");
	            ObjectOutputStream oos = new ObjectOutputStream(fos);
	            oos.writeObject(Global.getFormateur());
	            oos.close();
	     } catch (FileNotFoundException exp) {
	            exp.printStackTrace();
	     } catch (IOException exp) {
	            exp.printStackTrace();
	     }
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmlFilesFormateur/LogIn.fxml"));
        Parent menuParnet = loader.load();
        
        Scene menuFormateurScene = new Scene(menuParnet,1140,800);
      //Cette ligne retourne le Stage
        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();    
        
        
        menuParnet.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
         });
         menuParnet.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	window.setX(event.getScreenX() - xOffset);
            	window.setY(event.getScreenY() - yOffset);
            }
          });
        
        
        
        window.setScene(menuFormateurScene );
        window.show();
		
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
			home= FXMLLoader.load(getClass().getResource(nomFichier));
			setNode(home);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



}
