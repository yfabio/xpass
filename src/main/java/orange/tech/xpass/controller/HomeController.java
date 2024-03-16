package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import orange.tech.xpass.entity.Key;

@Component
public class HomeController extends BaseController {

	@FXML
	private Accordion filter;
	@FXML
	private TextField search;
	@FXML
	private Button searchBtn;
	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;
	@FXML
	private Button apply;
	
	@FXML
	private TableView<Key> keys;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
		
		
	}

	

}
