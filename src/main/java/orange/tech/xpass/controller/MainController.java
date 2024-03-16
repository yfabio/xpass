package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import orange.tech.xpass.fx.PlaceHolder;
import orange.tech.xpass.navigation.NavigationService;

@Component
public class MainController extends BaseController  {
	
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
	
	private NavigationService navigationService;
			
	@Autowired
	public MainController(NavigationService navigationService) {
		this.navigationService = navigationService;		
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

	
	
	
	
	
	
	
	
}
