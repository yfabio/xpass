package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;

@Component
public class RegisterController extends BaseController {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField confirmPassword;
	@FXML
	private Button register;
	
	private FxLoader fxLoader;
		
	public RegisterController(FxLoader fxLoader) {
		this.fxLoader = fxLoader;
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle rb) {
		register.setOnAction(evt -> {
			try {
				Stage stage = (Stage) ((Button) evt.getSource()).getScene().getWindow();

				Pane root = fxLoader.load(Url.LOGIN, null).navigate();

				Scene scene = new Scene(root);

				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	

	
}
