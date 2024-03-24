package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import net.synedra.validatorfx.Validator;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;
import orange.tech.xpass.property.Person;
import orange.tech.xpass.repository.PersonRepository;

@Component
public class RegisterController extends BaseController implements InvalidationListener {

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
	
	private Validator validator;
	


	public RegisterController(FxLoader fxLoader, 
			PersonRepository personRepository,
			ModelMapper modelMapper,
			Validator validator) {
		this.fxLoader = fxLoader;
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
		this.validator = validator;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {
		register.setOnAction(evt -> onRegisterHandler(evt));
		cancel.setOnAction(evt -> onCancelHandler(evt));
		
		username.textProperty().addListener(this);
		password.textProperty().addListener(this);
		confirmPassword.textProperty().addListener(this);
	}

	private void onCancelHandler(ActionEvent evt) {
		login(evt);
	}

	private void onRegisterHandler(ActionEvent evt) {
				
		validator.createCheck()
	     .dependsOn("username",username.textProperty())
	     .withMethod(c -> {
	    	 String username = c.get("username");
	    	 if(username.isBlank()) {
	    		 c.error("Please type username");
	    	 }
	     }).decorates(username).immediateClear();
		
		if(validateAndDisplay()) {return;}
						
		validator.createCheck()
	     .dependsOn("password",password.textProperty())
	     .withMethod(c -> {
	    	 String password = c.get("password");
	    	 if(password.isBlank()) {
	    		 c.error("Password is required");
	    	 }
	     }).decorates(password).immediateClear();
		
		if(validateAndDisplay()) {return;}
		
		validator.createCheck()
	     .dependsOn("confirm", confirmPassword.textProperty())
	     .withMethod(c -> {
	    	String confirm = c.get("confirm");
	    	String pass = password.getText();
	    	if(confirm.isBlank()) {
	    		c.error("Confirm Passowrd is required");
	    	}else if(!confirm.equals(pass)) {
	    		c.error("Passwords do not match");
	    	}
	     }).decorates(confirmPassword).immediateClear();
							
		if(validateAndDisplay()) {return;}
		
		var value = modelMapper.map(person, orange.tech.xpass.entity.Person.class);

		try {
			personRepository.save(value);
			username.textProperty().removeListener(this);
			password.textProperty().removeListener(this);
			confirmPassword.textProperty().removeListener(this);		
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

	@Override
	public void invalidated(Observable observable) {
		if(!error.getText().isBlank()) {error.setText("");}		
	}

	
	private boolean validateAndDisplay() {
		if(!validator.validate()) {				
			validator.getValidationResult()
			 .getMessages()
			 .forEach(c -> {
				 error.setText(c.getText());
			 });	
			return true;
		}
		return false;
	}
	
	
}
