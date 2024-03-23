package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import orange.tech.xpass.command.Command;
import orange.tech.xpass.fx.PlaceHolder;
import orange.tech.xpass.modal.Modal;
import orange.tech.xpass.modal.ModalFactory;
import orange.tech.xpass.modal.ModalOption;
import orange.tech.xpass.modal.OnModalAction;
import orange.tech.xpass.navigation.CallBackController;
import orange.tech.xpass.navigation.NavigationService;
import orange.tech.xpass.property.Key;
import orange.tech.xpass.property.Person;
import orange.tech.xpass.security.ApplicationLoggedUser;

@Component
public class MainController extends BaseController implements CallBackController<Stage>, OnModalAction, OnUpdateMainUI {

	@FXML
	private Button key;
	@FXML
	private Button list;
	@FXML
	private Button config;
	@FXML
	private ImageView profile;

	@FXML
	private Button minimize;
	@FXML
	private Button maximize;
	@FXML
	private Button close;

	@FXML
	private Label email;

	@FXML
	private PlaceHolder placeHolder;

	@FXML
	private StackPane modalRoot;

	@FXML
	private HBox modalContent;
	
	private double xOffset = 0;
	 
	private double yOffset = 0;

	private NavigationService navigationService;

	private ModalFactory modalFactory;

	private ApplicationLoggedUser applicationLoggedUser;

	private ModelMapper modalMapper;
	private Stage stage;

	@Autowired
	public MainController(NavigationService navigationService, ModalFactory modalFactory,
			ApplicationLoggedUser applicationLoggedUser, ModelMapper modalMapper) {
		this.navigationService = navigationService;
		this.modalFactory = modalFactory;
		this.applicationLoggedUser = applicationLoggedUser;
		this.modalMapper = modalMapper;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {

		if (!navigation.isBound()) {
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

		var mappedPerson = modalMapper.map(applicationLoggedUser.loggedUser(), Person.class);

		profile.imageProperty().bindBidirectional(mappedPerson.imageProperty());
		email.textProperty().bindBidirectional(mappedPerson.usernameProperty());

		minimize.setOnAction(evt -> onMinimizeHandler(evt));
		maximize.setOnAction(evt -> onMaximizeHandler(evt));
		close.setOnAction(evt -> onCloseHandler(evt));

	}

	private void onCloseHandler(ActionEvent evt) {
		stage.close();
	}

	private void onMaximizeHandler(ActionEvent evt) {
		if (!stage.isMaximized()) {
			stage.setMaximized(true);
		} else {
			stage.setMaximized(false);
		}
	}

	private void onMinimizeHandler(ActionEvent evt) {
		if (!stage.isIconified()) {
			stage.setIconified(true);
		} else {
			stage.setIconified(false);
		}
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
