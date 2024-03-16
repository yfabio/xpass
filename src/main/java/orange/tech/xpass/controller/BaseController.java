package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import orange.tech.xpass.navigation.Navigator;

public abstract class BaseController implements Initializable {

	protected static final ObjectProperty<Navigator> navigation = new SimpleObjectProperty<Navigator>();

	@Override
	public void initialize(URL arg0, ResourceBundle rb) {}
	
	

}
