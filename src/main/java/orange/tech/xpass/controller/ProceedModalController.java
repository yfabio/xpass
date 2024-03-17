package orange.tech.xpass.controller;

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

@Component
public class ProceedModalController extends BaseController {

	@FXML
	public PasswordField password;
	@FXML
	public Button ok;
	@FXML
	public Button cancel;
	
	public boolean passwordMatch() {
		// TODO call database
		
		if(password.getText().equals("123")) {
			return true;
		}else {
			return false;
		}		
	}
	
	

}
