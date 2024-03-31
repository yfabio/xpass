package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import jakarta.validation.Validator;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import orange.tech.xpass.crypto.Zippo;
import orange.tech.xpass.exception.ApplicationException;
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
	private Label back;
	@FXML
	private Label confirPasswordError;
	@FXML
	private Label passwordError;
	@FXML
	private Label usernameError;

	@FXML
	private Label error;

	@FXML
	private Button register;

	@FXML
	private Person person;

	private FxLoader fxLoader;

	private PersonRepository personRepository;

	private ModelMapper modelMapper;

	private Zippo zippo;

	private Validator validator;
	
	private InvalidationListener usernameErrorLister;
	private InvalidationListener passwordErrorLister;
	private InvalidationListener confirmPasswordErrorLister;


	public RegisterController(FxLoader fxLoader, PersonRepository personRepository, ModelMapper modelMapper,
			Validator validator, Zippo zippo) {
		this.fxLoader = fxLoader;
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
		this.zippo = zippo;
		this.validator = validator;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {
		register.setOnAction(evt -> onRegisterHandler(evt));
		back.setOnMouseClicked(evt -> onCancelHandler(evt));
		
		usernameErrorLister = e -> usernameError.setText("");
		passwordErrorLister = e -> passwordError.setText("");
		confirmPasswordErrorLister = e -> confirPasswordError.setText("");
		
		username.textProperty().addListener(usernameErrorLister);
		password.textProperty().addListener(passwordErrorLister);
		confirmPassword.textProperty().addListener(confirmPasswordErrorLister);
		
		
	}

	private void onCancelHandler(MouseEvent evt) {
		login(evt);
	}

	private void onRegisterHandler(ActionEvent evt) {

		try {
			var value = modelMapper.map(person, orange.tech.xpass.entity.Person.class);
			
			if (isPersonValid(value)) {
				return;
			} else {

				try {
										
					value.setPassword(zippo.encrypt(value.getPassword()));
					personRepository.save(value);
					
					username.textProperty().removeListener(usernameErrorLister);
					password.textProperty().removeListener(passwordErrorLister);
					confirmPassword.textProperty().removeListener(confirmPasswordErrorLister);
					
					login(evt);
				} catch (DataIntegrityViolationException e) {
					throw new ApplicationException("Username %s already exists".formatted(username.getText()));
				}
			}

		} catch (ApplicationException e) {
			error.setText(e.getMessage());
			resetError(error);
		} catch (Exception e) {
			showExceptionDialog(e);
		}

	}

	private void login(Event evt) {
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
	
	
	
	private boolean isPersonValid(orange.tech.xpass.entity.Person value) {
		
		var isUsernameValid = validator.validateProperty(value,"username");
		var isPasswordValid = validator.validateProperty(value,"password");
						
		if(!isUsernameValid.isEmpty()) {
			isUsernameValid.forEach(c -> usernameError.setText(c.getMessage()));
			return true;
		}
				
		if(!isPasswordValid.isEmpty()) {
			isPasswordValid.forEach(c -> passwordError.setText(c.getMessage()));
			return true;
		}
		
		if(!confirmPassword.getText().equals(value.getPassword())) {
			confirPasswordError.setText("passwods do not match");
			return true;
		}		
		
		return false;
	}
	
	
	
	

}
