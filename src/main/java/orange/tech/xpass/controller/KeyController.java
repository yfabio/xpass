package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import orange.tech.xpass.fx.PasswordField;

@Component
public class KeyController extends BaseController {

	@FXML
	private DatePicker date;
	@FXML
	private TextArea note;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button pwdHide;

	@FXML
	private Spinner<Integer> pwdLength;

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

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {

		pwdHide.graphicProperty().bind(Bindings.when(password.hideProperty()).then(open).otherwise(close));
		pwdHide.setOnAction(evt -> password.toggle());
	}

}
