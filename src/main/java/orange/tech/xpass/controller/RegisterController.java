package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orange.tech.xpass.crypto.Zippo;
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
	private Label error;
	
	@FXML
	private Button register;	
		
	@FXML
	private Button cancel;

	@FXML
	private Person person;

	private FxLoader fxLoader;

	private PersonRepository personRepository;

	private ModelMapper modelMapper;
		
	private Zippo zippo;

	public RegisterController(FxLoader fxLoader, 
			PersonRepository personRepository,
			ModelMapper modelMapper,		
			Zippo zippo) {
		this.fxLoader = fxLoader;
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;		
		this.zippo = zippo;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {
		register.setOnAction(evt -> onRegisterHandler(evt));
		cancel.setOnAction(evt -> onCancelHandler(evt));
		
	}

	private void onCancelHandler(ActionEvent evt) {
		login(evt);
	}

	private void onRegisterHandler(ActionEvent evt) {
					
		var value = modelMapper.map(person, orange.tech.xpass.entity.Person.class);

		try {
			
			value.setPassword(zippo.encrypt(value.getPassword()));
			
			personRepository.save(value);				
			login(evt);
		} catch (DataIntegrityViolationException e) {
			error.setText("Username %s already exists".formatted(username.getText()));
		}

	}

	private void login(ActionEvent evt) {
		try {
			Stage stage = (Stage) ((Control) evt.getSource()).getScene().getWindow();

			Pane root = fxLoader.load(Url.LOGIN, null).navigate();

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			error.setText(e.getMessage());
		}
	}

	
		
	
}
