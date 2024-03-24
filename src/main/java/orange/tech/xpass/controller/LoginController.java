package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import net.synedra.validatorfx.Validator;
import orange.tech.xpass.exception.ApplicationException;
import orange.tech.xpass.navigation.CallBackController;
import orange.tech.xpass.navigation.FxLoader;
import orange.tech.xpass.navigation.FxLoader.Url;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class LoginController extends BaseController implements CallBackController<Stage>, InvalidationListener {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;
	@FXML
	private Label register;

	@FXML
	private Button exit;
	
	@FXML
	private Label error;

	private FxLoader fxLoader;

	private ApplicationLoggedUser applicationLoggedUser;
	
	private Validator validator;
	
	private double xOffset;
	private double yOffset;
	private Stage stage;

	public LoginController(FxLoader fxLoader,
			ApplicationLoggedUser applicationLoggedUser,
			Validator validator) {
		this.applicationLoggedUser = applicationLoggedUser;
		this.fxLoader = fxLoader;
		this.validator = validator;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		register.setOnMouseClicked(evt -> onRegisterHandler(evt));
		login.setOnAction(evt -> onLoginHandler(evt));
		password.setOnAction(evt -> onLoginHandler(evt));
		exit.setOnAction(evt -> onExitHandler(evt));
		exit.setTooltip(new Tooltip("close app"));	
		
		username.textProperty().addListener(this);
		password.textProperty().addListener(this);
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
			
			validator.createCheck()
						.dependsOn("username",username.textProperty())
						.withMethod(c -> {
							String value = c.get("username");
							if(value.isBlank()){
								c.error("username is required");
							}
						}).decorates(username).immediateClear();
			
			if(validateAndDisplay()) {return;}
						
			validator.createCheck()
			.dependsOn("password",password.textProperty())
			.withMethod(c -> {
				String value = c.get("password");
				if(value.isBlank()){
					c.error("password is required");
				}
			}).decorates(password).immediateClear();
			
			if(validateAndDisplay()) {return;}
			
			username.textProperty().removeListener(this);
			password.textProperty().removeListener(this);

			applicationLoggedUser.tryLogin(username.getText(), password.getText());

			Pane root = fxLoader.load(Url.MAIN, () -> stage).navigate();

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
			
			

		} catch (ApplicationException e) {
			error.setText(e.getMessage());
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

	@Override
	public void invalidated(Observable observable) {
		if(!error.getText().isBlank()) {
			error.setText("");
		}		
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
