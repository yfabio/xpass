package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;
import orange.tech.xpass.repository.PersonRepository;

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
	
	private PersonRepository personRepository;

	public LoginController(FxLoader fxLoader,PersonRepository personRepository) {
		this.fxLoader = fxLoader;
		this.personRepository = personRepository;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		login.setOnAction(evt -> {
			try {
				Stage stage = (Stage) ((Button) evt.getSource()).getScene().getWindow();

				Pane root = fxLoader.load(Url.MAIN, null).navigate();

				Scene scene = new Scene(root);

				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		register.setOnMouseClicked(evt -> {
			try {
				Stage stage = (Stage) ((Label) evt.getSource()).getScene().getWindow();

				Pane root = fxLoader.load(Url.REGISTER, null).navigate();

				Scene scene = new Scene(root);

				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		personRepository.findAll().forEach(e -> System.out.println(e));
		
	}

}
