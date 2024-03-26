package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import orange.tech.xpass.crypto.Zippo;
import orange.tech.xpass.fx.PasswordField;
import orange.tech.xpass.modal.OnModalAction;
import orange.tech.xpass.navigation.NavigationService;
import orange.tech.xpass.property.Person;
import orange.tech.xpass.repository.PersonRepository;
import orange.tech.xpass.security.ApplicationLoggedUser;
import orange.tech.xpass.util.ImageUtil;

@Component
public class ConfigController extends BaseController {

	@FXML
	private TextField username;
	
	@FXML
	private TextField email;
	
	@FXML
	private ImageView profile;
	
	@FXML
	private Button upload;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button pwdHide;
	
	@FXML
	private FontIcon open;
	
	@FXML
	private FontIcon close;
	
	@FXML
	private Button save;
	
	@FXML
	private Button cancel;
	
	@FXML
	private Label error;
	
	@FXML
	private Person person;
	
	private PersonRepository personRepository;
	
	private NavigationService navigationService;
	
	private ApplicationLoggedUser applicationLoggedUser;
	
	private ModelMapper modelMapper;
	
	private ApplicationContext ctx;
	
	
	
	private Zippo zippo;

	public ConfigController(PersonRepository personRepository,
			ApplicationLoggedUser applicationLoggedUser,
			ModelMapper modelMapper,			
			Zippo zippo,
			NavigationService navigationService,
			ApplicationContext ctx) {
		this.personRepository = personRepository;
		this.applicationLoggedUser = applicationLoggedUser;		
		this.modelMapper = modelMapper;
		this.navigationService = navigationService;
		this.ctx = ctx;
		this.zippo = zippo;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		upload.setOnAction(evt -> onUploadImage(evt));		
		var mappedPerson = modelMapper.map(applicationLoggedUser.loggedUser(), Person.class);	
		mappedPerson.setPassword(zippo.decrypt(mappedPerson.getPassword()));
		person.idProperty().bindBidirectional(mappedPerson.idProperty());
		person.emailProperty().bindBidirectional(mappedPerson.emailProperty());		
		person.usernameProperty().bindBidirectional(mappedPerson.usernameProperty());
		person.imageProperty().bindBidirectional(mappedPerson.imageProperty());
		person.passwordProperty().bindBidirectional(mappedPerson.passwordProperty());		
		profile.imageProperty().bindBidirectional(mappedPerson.imageProperty());		
		person.setPhoto(mappedPerson.getPhoto());
				
		pwdHide.graphicProperty().bind(Bindings.when(password.hideProperty()).then(close).otherwise(open));
		pwdHide.setOnAction(evt -> onPasswordChanged());
		
		cancel.setOnAction(evt -> navigation.set(navigationService.getNavigator(HomeController.class)));		
		save.setOnAction(evt -> onSaveHandler());
		
		password.disableProperty().bind(password.hideProperty().not());
		

	}

	private void onPasswordChanged() {		
		if(!password.hideProperty().get()) {
			OnModalAction modal = ctx.getBean(MainController.class);
			modal.canProceed(password::toggle);
		}else {
			password.toggle();
		}
	}

	private void onSaveHandler() {		
		
		try {
			var mapped = modelMapper.map(person,orange.tech.xpass.entity.Person.class);			
			mapped.setPassword(zippo.encrypt(mapped.getPassword()));			
			applicationLoggedUser.replace(personRepository.save(mapped));			
			OnUpdateMainUI ui = ctx.getBean(MainController.class);
			ui.update(modelMapper.map(applicationLoggedUser.loggedUser(), Person.class));		
			navigation.set(navigationService.getNavigator(HomeController.class));
		} catch (Exception e) {
			error.setText("unable to save key");
		}
	}

	private void onUploadImage(ActionEvent evt) {
		Window window = ((Control) evt.getSource()).getScene().getWindow();
		ImageUtil.openFileDialog(window,person::setPhoto).ifPresent(profile::setImage);
	}
	
	
	

}
