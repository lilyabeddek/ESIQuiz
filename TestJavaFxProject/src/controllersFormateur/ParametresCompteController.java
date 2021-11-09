package controllersFormateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ParametresCompteController implements Initializable{
	
	
	private String imagePath="";
	
	@FXML private ImageView userPicture;
	@FXML private TextField userName;
	@FXML private TextField oldPassword;
	@FXML private TextField newPassword;
	
	
	//Trouver une nouvelle photo de profil
    @FXML private void parcourir(ActionEvent e) throws IOException {
    
    	FileChooser fc = new FileChooser();
		fc.setTitle("Photo de profil");
		FileChooser.ExtensionFilter extFilter =  new FileChooser.ExtensionFilter("Images", "*.bmp", "*.gif", "*.jpg", "*.jpeg", "*.png");
        fc.getExtensionFilters().add(extFilter);     
		 File fichier = fc.showOpenDialog(null);
		 if (fichier !=null) {

			 Image pic = new Image(fichier.toURI().toURL().toExternalForm());	 
			 userPicture.setImage(pic);
			 this.imagePath=fichier.getAbsolutePath();
			 
		 }
    }
    //verifier la validité de l'ancien et du nouveau mot de passe
    private boolean changePassword() {
    	
    	if (oldPassword.getText().equals("")) {
			if (!newPassword.getText().equals("")) {
				   Alert alert= new Alert(AlertType.ERROR);
				   alert.setTitle("Erreur");
				   alert.setContentText("Vous devez saisir votre ancien mot de passe");
				   alert.setHeaderText(null);
				   alert.showAndWait();
				   return false;
			}	
			else {return true;}
		}
		else {
			if (newPassword.getText().equals("")) {
				Alert alert= new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setContentText("Vous devez saisir votre nouveau mot de passe");
				alert.setHeaderText(null);
				alert.showAndWait();
				return false;
			}
			else {
				if (!oldPassword.getText().equals(Global.getFormateur().getPasswd())) {
					Alert alert= new Alert(AlertType.ERROR);
					alert.setTitle("Erreur");
					alert.setContentText("Ancien mot de passe incorrect,Ressayez !");
					alert.setHeaderText(null);
					alert.showAndWait();
					return false;
				}
				else {
					return true;
				}
			}
		}
    	
    }

    @FXML private void Enregistrer(ActionEvent e) throws IOException {
    	
    	if (changePassword()) {
    		if(!newPassword.getText().equals("")) {Global.getFormateur().setPasswd(newPassword.getText());}	
    		if(!userName.getText().equals("")) {
        		if (!userName.getText().equals(Global.getFormateur().getUsername())) {
        			System.out.println("on va changer");
        			Global.getFormateur().setUsername(userName.getText());
        		}
        		
        	}
    		
    		if (!imagePath.equals("")) {
       		 String[] tableStr= imagePath.split(Pattern.quote("\\"));		
   			 String imgDestination ="photos\\"+ Global.getFormateur().getUsername()+tableStr[tableStr.length-1];
   			 
   			 Global.getFormateur().setimage("file:"+imgDestination.replace("\\", "/"));
   			 Path source = Paths.get(imagePath);
   			 Path destination = Paths.get(imgDestination);
   			 Files.copy(source,destination,StandardCopyOption.REPLACE_EXISTING);	
   			 
   			    FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFilesFormateur/MenuFormateur.fxml"));
                Parent menuParnet = loader.load();            
                Scene menuFormateurScene = new Scene(menuParnet,1240,880);             
                MenuFormateurController controller = loader.getController();
                controller.paramCompte(e);
                     
                //Cette ligne retourne le Stage
                Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();                
                window.setScene(menuFormateurScene );
                window.show();
   			
               
   			 
           	}
    		 new Global().afficherNotif("Modifications éffectuées avec suucès !");  
    		
    	}
	
    }
    
    @FXML 
    private void supprimerCompte(ActionEvent e) throws IOException {
    	
    	Alert alert= new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText(null);
    	alert.setContentText("Etes vous sur de vouloir supprimer votre compte ?!");
    	alert.setTitle("Confirmation");
    	Optional<ButtonType> result = alert.showAndWait();
    	
    	if (result.get() == ButtonType.OK){
    		 FileOutputStream fos = null;
    	     try {  	    
    	            fos = new FileOutputStream("TestSerialisation.ser");
    	            fos.write("".getBytes());
    	            fos.close();
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
            window.setScene(menuFormateurScene );
            window.show();
            new Global().afficherNotif("Compte supprimé avec succès");  
    	} 
    	
    }
    

    //initialiser la fenetre avec la photo de profil et le nom d'utilisateur
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {userPicture.setImage(new Image(Global.getFormateur().getimage()));}
		catch(IllegalArgumentException e) {}		
		userName.setText(Global.getFormateur().getUsername());		
	}

}


