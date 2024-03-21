package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orange.tech.xpass.exception.ApplicationException;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class LoginController extends BaseController {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;
	@FXML
	private Label register;

	private FxLoader fxLoader;
	
	private ApplicationLoggedUser applicationLoggedUser;

	public LoginController(FxLoader fxLoader,ApplicationLoggedUser applicationLoggedUser) {
		this.applicationLoggedUser = applicationLoggedUser;		
		this.fxLoader = fxLoader;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {			
		register.setOnMouseClicked(evt -> onRegisterHandler(evt));		
		login.setOnAction(evt -> onLoginHandler(evt));
		password.setOnAction(evt -> onLoginHandler(evt));
	}

	private void onRegisterHandler(MouseEvent evt) {
		try {
			Stage stage = (Stage) ((Label) evt.getSource()).getScene().getWindow();

			Pane root = fxLoader.load(Url.REGISTER, null).navigate();

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			//TODO feedback error load register
		}
	}

	private void onLoginHandler(ActionEvent evt) {			
		
		try {
			
			applicationLoggedUser.tryLogin(username.getText(),
					password.getText());
						
			Stage stage = (Stage) ((Control) evt.getSource()).getScene().getWindow();

			Pane root = fxLoader.load(Url.MAIN, null).navigate();

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
			
		} catch (ApplicationException e) {
			//TODO feedback user login 
			System.out.println(e.getMessage());
		}
		
		
	}

}
