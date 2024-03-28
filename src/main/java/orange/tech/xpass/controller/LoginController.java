package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jakarta.validation.Validator;
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
	private Person person;

	@FXML
	private Button exit;

	@FXML
	private Label error;

	private FxLoader fxLoader;

	private ApplicationLoggedUser applicationLoggedUser;
	
	private ModelMapper modelMapper;
	
	private Validator validator;
	
	
	private double xOffset;
	private double yOffset;
	private Stage stage;
	

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
			
			var set = validator.validate(value);
			
			if(set.size() > 0) {
				
				var messages = messages(set);				
				error.setText(messages.toString());
				resetError(error);
				
			}else {
				
				applicationLoggedUser.tryLogin(person.getUsername(),person.getPassword());

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
