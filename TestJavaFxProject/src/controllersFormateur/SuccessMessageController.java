package controllersFormateur;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SuccessMessageController {
	
	@FXML private Label message;
	
	public void initMessage(String message) {
		this.message.setText(message);
	}
	
	
	 @FXML 
	 private void closeButtonAction(MouseEvent e){//fermer l'application
		
		 Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	     window.close();
	 }
	 

}
