package controllersApprenant;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import controllersFormateur.Global;
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

public class ApprenantHomeController implements Initializable{
	
	private double xOffset = 0; 
	private double yOffset = 0;
	
	@FXML private Label login;
	@FXML private ImageView profilPicture;
    @FXML private AnchorPane homepane;
    AnchorPane home;
    
  
    public static ApprenantHomeController instance;
    public ApprenantHomeController (){
        instance=this;
    }
    
	@FXML 
	private void accueil(ActionEvent e) {
		createPage("/fxmlFilesFormateur/Accueil.fxml");
	}
	
    @FXML
	public void paramCompte(ActionEvent e) {//chargement de la fenetre modification des parametres du compte du formateur
		createPage("/fxmlFilesApprenant/MonCompte.fxml");	
	}
        
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	//initialisation du nom d'utilisateur et photo de profil du formateur	
			login.setText(Global.getApprenant().getUsername());
			try {profilPicture.setImage(new Image(Global.getApprenant().getimage()));}
			catch(Exception e) {}		
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
       
    public static ApprenantHomeController getInstance(){
        return instance;
    }
  
    public void setNode(Node node) {
		
		homepane.getChildren().clear();
		homepane.getChildren().add((Node)node);
		FadeTransition ft= new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
		
	}
	public void createPage(String nomFichier) {
		try {	
			home= FXMLLoader.load(getClass().getResource(nomFichier));
			setNode(home);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    @FXML
    public void mesQuizs(ActionEvent event){
        createPage("/fxmlFilesApprenant/MesQuizs.fxml");

    }
    @FXML
    public void monCompte(ActionEvent event){
        createPage("/fxmlFilesApprenant/MonCompte.fxml");

    }
}
