package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;



public class Main extends Application {
	
	
	private double xOffset = 0; 
	private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			 Parent root = FXMLLoader.load(getClass().getResource("/fxmlFilesFormateur/LogIn.fxml"));
			
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
		        
			
			Scene scene = new Scene(root,1140,800);
			//scene.getStylesheets().add(getClass().getResource("bootstrap2.css").toExternalForm());	
			
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	
	}
	
	public static void main(String[] args) {
		
	   launch(args);
	}
}


