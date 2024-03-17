package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import orange.tech.xpass.command.Command;
import orange.tech.xpass.entity.Key;
import orange.tech.xpass.fx.PlaceHolder;
import orange.tech.xpass.modal.Modal;
import orange.tech.xpass.modal.ModalFactory;
import orange.tech.xpass.modal.ModalOption;
import orange.tech.xpass.modal.OnModalAction;
import orange.tech.xpass.navigation.NavigationService;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;

@Component
public class MainController extends BaseController implements OnModalAction  {
	
	@FXML
	private Button key;
	@FXML
	private Button list;
	@FXML
	private Button config;
	@FXML
	private ImageView profile;
	@FXML
	private Button logout;
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
			
	@Autowired
	public MainController(NavigationService navigationService,ModalFactory modalFactory) {
		this.navigationService = navigationService;		
		this.modalFactory = modalFactory;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {
		placeHolder.getNavigation().bindBidirectional(navigation);		
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

	
	
	
	
	
	
	
	
	
}
