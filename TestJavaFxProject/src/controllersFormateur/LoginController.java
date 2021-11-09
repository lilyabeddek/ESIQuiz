package controllersFormateur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import noyau.Apprenant;
import noyau.Formateur;




public class LoginController {
	
	
	private double xOffset = 0; 
	private double yOffset = 0;
	// controls de l'interface
	@FXML private TextField userNameField;
	@FXML private TextField passwordField;
	@FXML private Label userNameError;
	@FXML private Label passwordError;
	@FXML private Button BtnFormateur;
	@FXML private Button BtnApprenant;

	
	 @FXML public void clicUserNameField(MouseEvent e) {
		 userNameError.setVisible(false);
	 }
	 @FXML public void clicUserNameField2(InputMethodEvent e) {
		 userNameError.setVisible(false);
	 }
	
	 @FXML public void clicPsswdField(MouseEvent e) {
		 passwordError.setVisible(false);
	 }
	 @FXML public void clicPsswdField2(InputMethodEvent e) {
		 passwordError.setVisible(false);
	 }
	 
	 //fermer l'application
	 @FXML 
	 private void closeButtonAction(MouseEvent e){
		 Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	     window.close();
	 }
	//cette methode teste si les champs 'nom d'utilisateur' / 'mot de passe' sont vides
	 private boolean champsVides() {
	
		   if (userNameField.getText().equals("") && passwordField.getText().equals("")){
			   userNameError.setText("*Ce champ est obligatoire");
			   passwordError.setText("*Ce champ est obligatoire");
			   userNameError.setVisible(true);
			   passwordError.setVisible(true);
			   return true;
		   }
		   else if (userNameField.getText().equals("")){
			   userNameError.setText("*Ce champ est obligatoire");
			   userNameError.setVisible(true);
			   return true;
		   }
		   else if (passwordField.getText().equals("")){
			   passwordError.setText("*Ce champ est obligatoire");
			   passwordError.setVisible(true);
			   return true;
		   }
		   else {return false;}
	 }
	
	 // clic sur le bouton qui permet de se connecter en tant que formateur
	 @FXML public void conneterFormateur(ActionEvent event){
	   
	  if(!champsVides()) {
		  
		   File fich = new File("TestSerialisation.ser");
		   if(fich.length()==0) {//tester si le fichier est vide == premiere utilisation de l'application par le formateur

			   Alert alert= new Alert(AlertType.WARNING);
			   alert.setTitle("Attention !");
			   alert.setContentText("Il n'existe encore aucun formateur sur cette application, Veuillez vous inscrire avant !");
			   alert.setHeaderText(null);
			   alert.showAndWait();
	       }
		   else {
			   try {
				   //deserialization
		            FileInputStream fis= new FileInputStream("TestSerialisation.ser");
		            ObjectInputStream ois = new ObjectInputStream(fis);
		            Formateur form=(Formateur)ois.readObject();
		            fis.close();
		            ois.close();
		           
		            if (form.compareTo(userNameField.getText(), passwordField.getText())==-1) {
		            	userNameError.setText("*Nom d'utilisateur introuvable");    
		            	userNameError.setVisible(true);
		            }
		            else if (form.compareTo(userNameField.getText(), passwordField.getText())==0) {
		            	passwordError.setText("*Mot de passe incorrect");
		            	passwordError.setVisible(true);
		            }
		            else {
		            	 //sauvegarder le compte du Formateur en variable globale pour toutes les interfaces 
		            	if (form.hasFormation()) {form.copieQuizsTousApprenants();}
		            	
		            	Global.setFormateur(form);   
		            
		            	FXMLLoader loader = new FXMLLoader();
		                loader.setLocation(getClass().getResource("/fxmlFilesFormateur/MenuFormateur.fxml"));
		                Parent menuParnet = loader.load();            
		                Scene menuFormateurScene = new Scene(menuParnet,1240,890);             
		               
		                     
		                //Cette ligne retourne le Stage
		                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
		                
		                
		                		                
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
		            
		            

		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		        }
		   }
		  
	   }
      
    }
	
	 // clic sur le bouton qui permet de se connecter en tant qu'apprenant
	@FXML public void connecterApprenant(ActionEvent event){
	
		 if(!champsVides()) {
			 File fich = new File("TestSerialisation.ser");
			 if(fich.length()==0) {
				       Alert alert= new Alert(AlertType.WARNING);
					   alert.setTitle("Attention !");
					   alert.setContentText("Le formateur est responsable de la creation des comptes des apprenants et aucun formateur n'est inscrit pour l'instant !");
					   alert.setHeaderText(null);
					   alert.showAndWait();
	         }
			 else {
				 try {
			            FileInputStream fis= new FileInputStream("TestSerialisation.ser");
			            ObjectInputStream ois = new ObjectInputStream(fis);
			            Formateur formateur=(Formateur)ois.readObject();
			            ois.close();
			            formateur.afficherListeApprenants();
			            
			            int rang=formateur.rechApprenant(userNameField.getText(),passwordField.getText());
			            if (rang==-2) {
			            	userNameError.setText("Nom d'utilisateur introuvale");    
			            	userNameError.setVisible(true);
			            }
			            else if (rang==-1) {
			            	passwordError.setText("*Mot de passe incorrect");
			            	passwordError.setVisible(true);
			            }
			            else {
			            	
			            	Apprenant app= formateur.getApprenant(rang);
			            	if (formateur.hasFormation()) {formateur.copieQuizsTousApprenants();}
			            	
			            	Global.setFormateur(formateur);
			            	Global.setApprenant(app);
			            	
			            	FXMLLoader loader = new FXMLLoader();
			                loader.setLocation(getClass().getResource("/fxmlFilesApprenant/Apprennant_home.fxml"));
			                Parent menuParnet = loader.load();            
			                Scene menuFormateurScene = new Scene(menuParnet,1240,880);             
			               
			                     
			                //Cette ligne retourne le Stage
			                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();     
			                
			                
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
			           
			        
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } catch (ClassNotFoundException e) {
			            e.printStackTrace();
			        }
			 }
		  
	   }
      
    }
	//clic sur le bouton qui permet de s'inscrire en tant que formateur
	@FXML public void inscrireFormateur(ActionEvent event) throws IOException{
		 if(champsVides()==false) {
			 File fich = new File("TestSerialisation.ser");
			 if(fich.length()!=0) {	 
				    Alert alert= new Alert(AlertType.WARNING);
					alert.setTitle("Attention !");
					alert.setContentText("Cette application et uniformateur et il en existe déjà un !");
					alert.setHeaderText(null);
					alert.showAndWait();
		     }
			 else if(!champsVides()) {
				 Formateur newFormateur= new Formateur(userNameField.getText(),passwordField.getText());
				 Global.setFormateur(newFormateur);
				 
				 FXMLLoader loader = new FXMLLoader();
	             loader.setLocation(getClass().getResource("/fxmlFilesFormateur/MenuFormateur.fxml"));
	             Parent menuParnet = loader.load();            
	             Scene menuFormateurScene = new Scene(menuParnet,1240,890);                   
	             //Cette ligne retourne le Stage
	             Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
	             
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
				 
	             new Global().afficherNotif("Connecté avec succès,Bienvenue sur Esi Quiz !");
			 }
		 }
	}
}
