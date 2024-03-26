package orange.tech.xpass.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.kordamp.ikonli.javafx.FontIcon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import orange.tech.xpass.builder.Builder;
import orange.tech.xpass.builder.PasswordBuilder;
import orange.tech.xpass.crypto.Zippo;
import orange.tech.xpass.fx.PasswordField;
import orange.tech.xpass.navigation.CallBackController;
import orange.tech.xpass.navigation.NavigationService;
import orange.tech.xpass.property.Key;
import orange.tech.xpass.repository.KeyRepository;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class KeyController extends BaseController implements CallBackController<Key> {

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
	private Label dateError;
	@FXML
	private Label userEmailError;
	@FXML
	private Label noteError;
	@FXML
	private Label passwordError;

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

	private Zippo zippo;
	
	private List<Label> errors = new ArrayList<>();
	
	private InvalidationListener dateErrorListenner;
	private InvalidationListener userEmailErrorListenner;
	private InvalidationListener noteErrorListenner;
	private InvalidationListener passwordErrorListenner;
	
	
	public KeyController(NavigationService navigationService, KeyRepository keyRepository, ModelMapper modelMapper,
			ApplicationLoggedUser applicationLoggedUser, Validator validator, Zippo zippo) {
		this.navigationService = navigationService;
		this.keyRepository = keyRepository;
		this.modelMapper = modelMapper;
		this.validator = validator;
		this.applicationLoggedUser = applicationLoggedUser;
		this.zippo = zippo;
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

		
		dateErrorListenner = e -> dateError.setText("");;
		userEmailErrorListenner = e -> userEmailError.setText("");
		noteErrorListenner = e -> noteError.setText("");
		passwordErrorListenner = e -> passwordError.setText("");		
		
		date.valueProperty().addListener(dateErrorListenner);
		username.textProperty().addListener(userEmailErrorListenner);
		note.textProperty().addListener(noteErrorListenner);
		password.textProperty().addListener(passwordErrorListenner);
		
		errors.add(dateError);
		errors.add(userEmailError);
		errors.add(noteError);
		errors.add(passwordError);
		
		
	}

	private void onSaveHandler() {

		validator.createCheck().dependsOn("date", key.dateProperty()).withMethod(c -> {
			LocalDate date = c.get("date");
			if (date == null) {
				c.error("");
				dateError.setText("Please choose a date");
			}
		}).decorates(date).immediateClear();

		validator.createCheck().dependsOn("note",key.noteProperty()).withMethod(c -> {
			String note = c.get("note");
			if (note.isBlank()) {
				c.error("");
				noteError.setText("Please type a description");
			}
		}).decorates(note).immediateClear();

		validator.createCheck().dependsOn("username", key.usernameProperty()).withMethod(c -> {
			String username = c.get("username");
			if (username.isBlank()) {
				c.error("");
				userEmailError.setText("Please type username");
			}
		}).decorates(username).immediateClear();

		validator.createCheck().dependsOn("password",key.passwordProperty()).withMethod(c -> {
			String password = c.get("password");
			if (password.isBlank()) {
				c.error("");
				passwordError.setText("Password is required");
			} else if (password.length() < 4) {
				c.error("");
				passwordError.setText("Password length minimum 4");
			}
		}).decorates(password).immediateClear();

		if (validateAndDisplay()) {
			return;
		}

		try {
			var value = modelMapper.map(key, orange.tech.xpass.entity.Key.class);
			value.setPerson(applicationLoggedUser.loggedUser());

			value.setPassword(zippo.encrypt(value.getPassword()));

			keyRepository.save(value);

			date.valueProperty().removeListener(dateErrorListenner);
			username.textProperty().removeListener(userEmailErrorListenner);
			note.textProperty().removeListener(noteErrorListenner);
			password.textProperty().removeListener(passwordErrorListenner);

			key.reset();
			Platform.runLater(date::requestFocus);

		} catch (Exception e) {			
		}
	}

	private boolean validateAndDisplay() {
		if (!validator.validate()) {			
			return true;
		}
		return false;
	}

	private void generatePassword() {
		password.textProperty().setValue(builder.length(spinner.getValue()).lower(lowerLetters.isSelected())
				.upper(upperLetters.isSelected()).number(numbers.isSelected()).symbol(symbols.isSelected()).build());
	}

	@Override
	public void content(Supplier<Key> sup) {
		key.setData(sup.get());
	}

	

}
