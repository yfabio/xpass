package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;
import orange.tech.xpass.property.Person;
import orange.tech.xpass.repository.PersonRepository;

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
	
	@FXML 
	private Person person;
	
	private FxLoader fxLoader;
	
	private PersonRepository personRepository;
	
	private ModelMapper modelMapper;
	
		
	public RegisterController(FxLoader fxLoader,
			PersonRepository personRepository,
			ModelMapper modelMapper) {
		this.fxLoader = fxLoader;
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle rb) {
		register.setOnAction(evt -> onRegisterHandler(evt));		
	}


	private void onRegisterHandler(ActionEvent evt) {
		
		var pwdOne = password.getText();
		var pwdTwo = confirmPassword.getText();
			
		if(pwdOne.equals(pwdTwo)) {		
						
			var value = modelMapper.map(person, orange.tech.xpass.entity.Person.class);
			
			personRepository.save(value);
			
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
			
		}
		
		
		
	}

	

	
}
