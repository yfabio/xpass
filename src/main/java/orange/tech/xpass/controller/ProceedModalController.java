package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import orange.tech.xpass.crypto.Zippo;
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
	
	private Zippo zippo;
	
	public ProceedModalController(ApplicationLoggedUser applicationLoggedUser,Zippo zippo) {
		this.applicationLoggedUser = applicationLoggedUser;
		this.zippo = zippo;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Platform.runLater(password::requestFocus);
	}

	public boolean doesPasswordMatch() {		
		if(password.getText().equals(zippo.decrypt(applicationLoggedUser.loggedUser().getPassword()))) {
			return true;
		}else {
			return false;
		}		
	}
	
	

}
