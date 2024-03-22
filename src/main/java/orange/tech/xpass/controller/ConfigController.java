package orange.tech.xpass.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private Person person;
	
	@FXML
	private Button save;
	
	@FXML
	private Button cancel;
	
	private PersonRepository personRepository;
	
	private NavigationService navigationService;
	
	private ApplicationLoggedUser applicationLoggedUser;
	
	private ModelMapper modelMapper;
	
	private ApplicationContext ctx;
	

	public ConfigController(PersonRepository personRepository,
			ApplicationLoggedUser applicationLoggedUser,
			ModelMapper modelMapper,
			NavigationService navigationService,
			ApplicationContext ctx) {
		this.personRepository = personRepository;
		this.applicationLoggedUser = applicationLoggedUser;
		this.modelMapper = modelMapper;
		this.navigationService = navigationService;
		this.ctx = ctx;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		upload.setOnAction(evt -> onUploadImage(evt));		
		var mappedPerson = modelMapper.map(applicationLoggedUser.loggedUser(), Person.class);	
		person.idProperty().bindBidirectional(mappedPerson.idProperty());
		person.emailProperty().bindBidirectional(mappedPerson.emailProperty());		
		person.usernameProperty().bindBidirectional(mappedPerson.usernameProperty());
		person.imageProperty().bindBidirectional(mappedPerson.imageProperty());
		person.passwordProperty().bindBidirectional(mappedPerson.passwordProperty());		
		profile.imageProperty().bindBidirectional(mappedPerson.imageProperty());		
		person.setPhoto(mappedPerson.getPhoto());
		
		
		cancel.setOnAction(evt -> navigation.set(navigationService.getNavigator(HomeController.class)));		
		save.setOnAction(evt -> onSaveHandler());

	}

	private void onSaveHandler() {
		var mapped = modelMapper.map(person,orange.tech.xpass.entity.Person.class);
		applicationLoggedUser.replace(personRepository.save(mapped));		
		OnUpdateMainUI ui = ctx.getBean(MainController.class);
		ui.update(modelMapper.map(applicationLoggedUser.loggedUser(), Person.class));		
		navigation.set(navigationService.getNavigator(HomeController.class));
	}

	private void onUploadImage(ActionEvent evt) {
		Optional<Image> op = ImageUtil.openFileDialog(((Control) evt.getSource()).getScene().getWindow(),data -> {
			person.setPhoto(data);
		});
		if (op.isPresent()) {
			profile.setImage(op.get());
		}
	}

}
