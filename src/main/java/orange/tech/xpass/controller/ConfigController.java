package orange.tech.xpass.controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

@Component
public class ConfigController extends BaseController {

	@FXML
	private TextField username;
	@FXML
	private TextField email;
	@FXML
	private TextField path;
	@FXML
	private ImageView profile;
	@FXML
	private Button save;
	@FXML
	private Button cancel;

	
	
}
