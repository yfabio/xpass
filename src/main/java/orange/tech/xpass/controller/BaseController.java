package orange.tech.xpass.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import jakarta.validation.ConstraintViolation;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import orange.tech.xpass.navigation.Navigator;

public abstract class BaseController implements Initializable {

	protected static final ObjectProperty<Navigator> navigation = new SimpleObjectProperty<Navigator>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {}
	
	private ScheduledExecutorService job = Executors.newSingleThreadScheduledExecutor();
	
	
	public void resetError(Label lb) {
		job.schedule(() -> Platform.runLater(() -> lb.setText("")), 2, TimeUnit.SECONDS);
	}
	
	protected StringBuilder messages(Set<ConstraintViolation<orange.tech.xpass.entity.Person>> set) {
		Stream<String> stream = set.stream().map(e -> e.getMessage());
		
		var messages = stream.collect(StringBuilder::new,(sb,s) -> sb.append(s)
				  			 .append(System.lineSeparator()), (x,y) -> x.append(y));
		return messages;
	}
	
	

}
