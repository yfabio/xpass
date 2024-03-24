package orange.tech.xpass.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.kordamp.ikonli.javafx.FontIcon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import orange.tech.xpass.builder.Builder;
import orange.tech.xpass.builder.PasswordBuilder;
import orange.tech.xpass.fx.PasswordField;
import orange.tech.xpass.navigation.CallBackController;
import orange.tech.xpass.navigation.NavigationService;
import orange.tech.xpass.property.Key;
import orange.tech.xpass.repository.KeyRepository;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class KeyController extends BaseController implements CallBackController<Key>, InvalidationListener {

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

	private KeyRepository keyRepository;

	private Builder builder = PasswordBuilder.getInstance();

	private ModelMapper modelMapper;

	private ApplicationLoggedUser applicationLoggedUser;

	private Validator validator;

	@FXML
	private Label error;

	public KeyController(NavigationService navigationService, KeyRepository keyRepository, ModelMapper modelMapper,
			ApplicationLoggedUser applicationLoggedUser, Validator validator) {
		this.navigationService = navigationService;
		this.keyRepository = keyRepository;
		this.modelMapper = modelMapper;
		this.validator = validator;
		this.applicationLoggedUser = applicationLoggedUser;
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

		key.idProperty().addListener((obs, x, y) -> {
			save.textProperty().bind(Bindings.when(key.idProperty().greaterThan(0)).then("edit").otherwise("save"));
		});

		generate.setOnAction(evt -> generatePassword());
		save.setOnAction(evt -> onSaveHandler());
		Platform.runLater(date::requestFocus);

		date.valueProperty().addListener(this);
		note.textProperty().addListener(this);
		username.textProperty().addListener(this);
		password.textProperty().addListener(this);

	}

	private void onSaveHandler() {

		validator.createCheck().dependsOn("date", date.valueProperty()).withMethod(c -> {
			LocalDate date = c.get("date");
			if (date == null) {
				c.error("Please choose a date");
			}
		}).decorates(date).immediateClear();

		if (validateAndDisplay()) {
			return;
		}

		validator.createCheck().dependsOn("note", note.textProperty()).withMethod(c -> {
			String note = c.get("note");
			if (note.isBlank()) {
				c.error("Please type a description");
			}
		}).decorates(note).immediateClear();

		if (validateAndDisplay()) {
			return;
		}

		validator.createCheck().dependsOn("username", username.textProperty()).withMethod(c -> {
			String username = c.get("username");
			if (username.isBlank()) {
				c.error("Please type username");
			}
		}).decorates(username).immediateClear();

		if (validateAndDisplay()) {
			return;
		}

		validator.createCheck().dependsOn("password", password.textProperty()).withMethod(c -> {
			String password = c.get("password");
			if (password.isBlank()) {
				c.error("Password is required");
			} else if (password.length() < 4) {
				c.error("Password length minimum 4");
			}
		}).decorates(password).immediateClear();

		if (validateAndDisplay()) {
			return;
		}

		try {
			var value = modelMapper.map(key, orange.tech.xpass.entity.Key.class);
			value.setPerson(applicationLoggedUser.loggedUser());
			keyRepository.save(value);

			date.valueProperty().addListener(this);
			note.textProperty().addListener(this);
			username.textProperty().addListener(this);
			password.textProperty().addListener(this);

			key.reset();
			Platform.runLater(date::requestFocus);

		} catch (Exception e) {
			error.setText("unable to save");
		}
	}

	private boolean validateAndDisplay() {
		if (!validator.validate()) {
			validator.getValidationResult().getMessages().forEach(c -> {
				error.setText(c.getText());
			});
			return true;
		}
		return false;
	}

	private void generatePassword() {
		password.setText(builder.length(spinner.getValue()).lower(lowerLetters.isSelected())
				.upper(upperLetters.isSelected()).number(numbers.isSelected()).symbol(symbols.isSelected()).build());
	}

	@Override
	public void content(Supplier<Key> sup) {
		key.setData(sup.get());
	}

	@Override
	public void invalidated(Observable observable) {
		if (!error.getText().isBlank()) {
			error.setText("");
		}
	}

}
