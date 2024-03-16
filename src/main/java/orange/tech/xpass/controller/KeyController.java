package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import orange.tech.xpass.entity.Key;
import orange.tech.xpass.fx.PasswordField;
import orange.tech.xpass.navigation.CallBackController;
import orange.tech.xpass.navigation.NavigationService;

@Component
public class KeyController extends BaseController implements CallBackController<Key> {

	@FXML
	private DatePicker date;
	
	@FXML
	private TextField note;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button pwdHide;

	@FXML
	private Spinner<Integer> spinner;

	@FXML
	private CheckBox upperLetters;

	@FXML
	private CheckBox lowerLetters;

	@FXML
	private CheckBox numbers;

	@FXML
	private CheckBox symbols;

	@FXML
	private Button generate;

	@FXML
	private Button save;
	@FXML
	private Button cancel;

	@FXML
	private FontIcon open;

	@FXML
	private FontIcon close;

	@FXML
	private Key key;

	private NavigationService navigationService;

	public KeyController(NavigationService navigationService) {
		this.navigationService = navigationService;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		pwdHide.graphicProperty().bind(Bindings.when(password.hideProperty()).then(open).otherwise(close));
		pwdHide.setOnAction(evt -> password.toggle());

		save.setOnAction(evt -> {
			System.out.println(key);
		});

		cancel.setOnAction(evt -> {
			navigation.set(navigationService.getNavigator(HomeController.class));
		});
	}

	@Override
	public void content(Supplier<Key> sup) {
		key.setData(sup.get());
	}

}
