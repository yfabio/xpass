package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.kordamp.ikonli.javafx.FontIcon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import jakarta.validation.Validator;
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
	
	private InvalidationListener dateErrorListener;
	private InvalidationListener userEmailErrorListener;
	private InvalidationListener noteErrorListener;
	private InvalidationListener passwordErrorListener;

	
	public KeyController(NavigationService navigationService, KeyRepository keyRepository, ModelMapper modelMapper,
			ApplicationLoggedUser applicationLoggedUser,Zippo zippo,jakarta.validation.Validator validator) {
		this.navigationService = navigationService;
		this.keyRepository = keyRepository;
		this.modelMapper = modelMapper;		
		this.applicationLoggedUser = applicationLoggedUser;
		this.zippo = zippo;
		this.validator = validator;
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
		
		dateErrorListener = e -> dateError.setText("");
		userEmailErrorListener = e -> userEmailError.setText("");
		noteErrorListener = e -> noteError.setText("");
		passwordErrorListener = e -> passwordError.setText("");
		
		date.valueProperty().addListener(dateErrorListener);
		username.textProperty().addListener(userEmailErrorListener);
		note.textProperty().addListener(noteErrorListener);
		password.textProperty().addListener(passwordErrorListener);
						
		
	}

	private void onSaveHandler() {
		

		try {
						
			var value = modelMapper.map(key, orange.tech.xpass.entity.Key.class);
								
			if(isKeyValid(value)) {
				return;
			}else {
				value.setPerson(applicationLoggedUser.loggedUser());

				value.setPassword(zippo.encrypt(value.getPassword()));

				keyRepository.save(value);
				
				date.valueProperty().removeListener(dateErrorListener);
				username.textProperty().removeListener(userEmailErrorListener);
				note.textProperty().removeListener(noteErrorListener);
				password.textProperty().removeListener(passwordErrorListener);

				key.reset();
				Platform.runLater(date::requestFocus);	
			}
			
			
		} catch (Exception e) {			
		}
	}

	

	private void generatePassword() {
		password.textProperty().setValue(builder.length(spinner.getValue()).lower(lowerLetters.isSelected())
				.upper(upperLetters.isSelected()).number(numbers.isSelected()).symbol(symbols.isSelected()).build());
	}

	@Override
	public void content(Supplier<Key> sup) {
		key.setData(sup.get());
	}

	
	private boolean isKeyValid(orange.tech.xpass.entity.Key value) {
				
		var isDateValid = validator.validateProperty(value,"date");
		var isUsernameValid = validator.validateProperty(value,"username");
		var isNoteValid  =validator.validateProperty(value,"note");
		var isPasswordValid = validator.validateProperty(value,"password");
		
		if(!isDateValid.isEmpty()) {
			isDateValid.forEach(c -> dateError.setText(c.getMessage()));
			return true;
		}
		
		if(!isUsernameValid.isEmpty()) {
			isUsernameValid.forEach(c -> userEmailError.setText(c.getMessage()));
			return true;
		}
		
		if(!isNoteValid.isEmpty()) {
			isNoteValid.forEach(c -> noteError.setText(c.getMessage()));
			return true;
		}
		
		if(!isPasswordValid.isEmpty()) {
			var messages =isPasswordValid.stream().map(c -> c.getMessage()).collect(Collectors.joining(","));
			passwordError.setText(messages);
			return true;
		}
		
		
		return false;
	}
	

}
