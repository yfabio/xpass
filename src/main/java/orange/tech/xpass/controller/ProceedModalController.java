package orange.tech.xpass.controller;

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import orange.tech.xpass.security.ApplicationLoggedUser;
import javafx.scene.control.Button;

@Component
public class ProceedModalController extends BaseController {

	@FXML
	public PasswordField password;
	@FXML
	public Button ok;
	@FXML
	public Button cancel;
	
	private ApplicationLoggedUser applicationLoggedUser;
	
	public ProceedModalController(ApplicationLoggedUser applicationLoggedUser) {
		this.applicationLoggedUser = applicationLoggedUser;
	}

	public boolean passwordMatch() {		
		if(password.getText().equals(applicationLoggedUser.loggedUser().getPassword())) {
			return true;
		}else {
			return false;
		}		
	}
	
	

}
