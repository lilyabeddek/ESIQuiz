package controllersFormateur;

import java.io.IOException;
import java.io.Serializable;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import noyau.*;
public class Global implements Serializable{
	
	
	 private double xOffset = 0; 
	 private double yOffset = 0;
	 
	private static final long serialVersionUID = 1L;
	public static Formateur formateur;
	public static Apprenant apprenant;

	public static Formateur getFormateur() {
		return formateur;
	}

	public static void setFormateur(Formateur formateur) {
		Global.formateur = formateur;
	}
	
	
	public static Apprenant getApprenant() {
		return apprenant;
	}

	public static void setApprenant(Apprenant apprenant) {
		Global.apprenant = apprenant;
	}
	
	public void afficherNotif(String message) throws IOException {
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmlFilesFormateur/SuccessMessage.fxml"));
        Parent root  = loader.load(); 
	 
		 root.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	      });
	      root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	primaryStage.setX(event.getScreenX() - xOffset);
	            	primaryStage.setY(event.getScreenY() - yOffset);
	            }
	       });
	        
		SuccessMessageController controller= loader.getController();
		controller.initMessage(message);
		Scene scene = new Scene(root,593,115);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	 
       Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
       primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()-10));
       primaryStage.setY(10);
       
       PauseTransition delay = new PauseTransition(Duration.seconds(3));
       delay.setOnFinished( e -> {
           Timeline timeline = new Timeline();
           KeyFrame key = new KeyFrame(Duration.millis(2000),
                          new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 0)); 
           timeline.getKeyFrames().add(key);   
           timeline.setOnFinished((ae) -> primaryStage.close()); 
           timeline.play();
       });
       delay.play();
       
       
	}

}
