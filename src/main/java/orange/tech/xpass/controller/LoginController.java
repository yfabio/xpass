package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

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
	private Button exit;

	private FxLoader fxLoader;

	private ApplicationLoggedUser applicationLoggedUser;
	
	private double xOffset;
	private double yOffset;
	private Stage stage;

	public LoginController(FxLoader fxLoader, ApplicationLoggedUser applicationLoggedUser) {
		this.applicationLoggedUser = applicationLoggedUser;
		this.fxLoader = fxLoader;
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
			// TODO feedback error load register
		}
	}

	private void onLoginHandler(ActionEvent evt) {

		try {

			applicationLoggedUser.tryLogin(username.getText(), password.getText());

			Pane root = fxLoader.load(Url.MAIN, () -> stage).navigate();

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();

		} catch (ApplicationException e) {
			// TODO feedback user login
			System.out.println(e.getMessage());
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
