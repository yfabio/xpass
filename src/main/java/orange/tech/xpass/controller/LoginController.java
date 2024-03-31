package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jakarta.validation.Validator;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orange.tech.xpass.exception.ApplicationException;
import orange.tech.xpass.navigation.CallBackController;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;
import orange.tech.xpass.property.Person;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class LoginController extends BaseController implements CallBackController<Stage> {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;
	@FXML
	private Label register;
	
	@FXML
	private Label usernameError;
	
	@FXML
	private Label passwordError;
	
	@FXML 
	private Person person;

	@FXML
	private Button exit;

	@FXML
	private Label error;
	
	private double xOffset;
	
	private double yOffset;
	
	private Stage stage;
	
	private FxLoader fxLoader;

	private ApplicationLoggedUser applicationLoggedUser;
	
	private ModelMapper modelMapper;
	
	private Validator validator;
		
	private InvalidationListener usernameErroListener;
	private InvalidationListener passwordErroListener;

	public LoginController(FxLoader fxLoader, ApplicationLoggedUser applicationLoggedUser,
			ModelMapper modelMapper,
			Validator validator) {
		this.applicationLoggedUser = applicationLoggedUser;
		this.fxLoader = fxLoader;
		this.modelMapper = modelMapper;
		this.validator = validator;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		register.setOnMouseClicked(evt -> onRegisterHandler(evt));
		login.setOnAction(evt -> onLoginHandler(evt));
		password.setOnAction(evt -> onLoginHandler(evt));
		exit.setOnAction(evt -> onExitHandler(evt));
		exit.setTooltip(new Tooltip("close app"));
		
		usernameErroListener = e -> usernameError.setText("");		
		passwordErroListener = e -> passwordError.setText("");	
		
		username.textProperty().addListener(usernameErroListener);
		password.textProperty().addListener(passwordErroListener);
		
	}

	private void onExitHandler(ActionEvent evt) {
		stage.close();
	}

	private void onRegisterHandler(MouseEvent evt) {
		try {
			Pane root = fxLoader.load(Url.REGISTER, null).navigate();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (Exception e) {
			error.setText(e.getMessage());
		}
	}

	private void onLoginHandler(ActionEvent evt) {

		try {
			
			var value  = modelMapper.map(person, orange.tech.xpass.entity.Person.class);
								
			if(isPersonValid(value)) {
				return;				
			}else {
				
				applicationLoggedUser.tryLogin(person.getUsername(),person.getPassword());
				
				username.textProperty().removeListener(usernameErroListener);
				password.textProperty().removeListener(passwordErroListener);

				Pane root = fxLoader.load(Url.MAIN, () -> stage).navigate();

				Scene scene = new Scene(root);

				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
			}
			
			

		} catch (ApplicationException e) {
			error.setText(e.getMessage());
			resetError(error);
		}catch (Exception e) {
			showExceptionDialog(e);
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
		
		return false;
	}

	@FXML
	public void onMouseDraggedHandler(MouseEvent evt) {
		stage.setX(evt.getScreenX() + xOffset);
		stage.setY(evt.getScreenY() + yOffset);
	}

	@FXML
	public void onMousePressedHandler(MouseEvent evt) {
		xOffset = stage.getX() - evt.getScreenX();
		yOffset = stage.getY() - evt.getScreenY();
	}

	@Override
	public void content(Supplier<Stage> sup) {
		this.stage = sup.get();
	}

	
	
	
}
