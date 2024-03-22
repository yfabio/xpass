package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import orange.tech.xpass.command.Command;
import orange.tech.xpass.fx.PlaceHolder;
import orange.tech.xpass.modal.Modal;
import orange.tech.xpass.modal.ModalFactory;
import orange.tech.xpass.modal.ModalOption;
import orange.tech.xpass.modal.OnModalAction;
import orange.tech.xpass.navigation.NavigationService;
import orange.tech.xpass.property.Key;
import orange.tech.xpass.property.Person;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class MainController extends BaseController implements OnModalAction, OnUpdateMainUI {

	@FXML
	private Button key;
	@FXML
	private Button list;
	@FXML
	private Button config;
	@FXML
	private ImageView profile;
	
	@FXML
	private Label email;

	@FXML
	private PlaceHolder placeHolder;

	@FXML
	private StackPane modalRoot;

	@FXML
	private HBox modalContent;

	private NavigationService navigationService;

	private ModalFactory modalFactory;
	
	private ApplicationLoggedUser applicationLoggedUser;
	
	private ModelMapper modalMapper;
	
	@Autowired
	public MainController(NavigationService navigationService, 
			ModalFactory modalFactory,
			ApplicationLoggedUser applicationLoggedUser,
			ModelMapper modalMapper) {
		this.navigationService = navigationService;
		this.modalFactory = modalFactory;		
		this.applicationLoggedUser = applicationLoggedUser;
		this.modalMapper = modalMapper;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {	
		
		if(!navigation.isBound()) {
			placeHolder.getNavigation().bindBidirectional(navigation);
		}
		
		navigation.set(navigationService.getNavigator(HomeController.class));

		key.setOnAction(evt -> {
			navigation.set(navigationService.getNavigator(KeyController.class));
		});

		list.setOnAction(evt -> {
			navigation.set(navigationService.getNavigator(HomeController.class));
		});

		config.setOnAction(evet -> {
			navigation.set(navigationService.getNavigator(ConfigController.class));
		});
		
		var mappedPerson = modalMapper.map(applicationLoggedUser.loggedUser(),Person.class);
		
		profile.imageProperty().bindBidirectional(mappedPerson.imageProperty());
		email.textProperty().bindBidirectional(mappedPerson.usernameProperty());
		
		
	}

	@Override
	public void delete(Key key, Command cmd) {
		Modal modal = modalFactory.get(ModalOption.DELETE);
		modal.open(modalRoot::setVisible);
		modal.show(modalContent.getChildren()::setAll);
		modal.done(() -> {
			modal.close(modalRoot::setVisible);
			cmd.execute();
		});
		modal.cancel(() -> {
			modal.close(modalRoot::setVisible);
		});
	}

	@Override
	public void canProceed(Command cmd) {
		Modal modal = modalFactory.get(ModalOption.PROCEED);
		modal.open(modalRoot::setVisible);
		modal.show(modalContent.getChildren()::setAll);
		modal.done(() -> {
			modal.close(modalRoot::setVisible);
			cmd.execute();
		});
		modal.cancel(() -> {
			modal.close(modalRoot::setVisible);
		});
	}

	@Override
	public void update(Person p) {
		profile.imageProperty().bindBidirectional(p.imageProperty());
		email.textProperty().bindBidirectional(p.usernameProperty());
	}

}
