package orange.tech.xpass.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import orange.tech.xpass.navigation.Navigator;

public abstract class BaseController implements Initializable {

	protected static final ObjectProperty<Navigator> navigation = new SimpleObjectProperty<Navigator>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {}
	
	private ScheduledExecutorService job;
	
	
	public void resetError(Label lb) {
		job = Executors.newSingleThreadScheduledExecutor();
		job.schedule(() -> Platform.runLater(() -> lb.setText("")), 2, TimeUnit.SECONDS);
		job.shutdown();
	}
	
	protected StringBuilder messages(Set<ConstraintViolation<orange.tech.xpass.entity.Person>> set) {
		Stream<String> stream = set.stream().map(e -> e.getMessage());
		
		var messages = stream.collect(StringBuilder::new,(sb,s) -> sb.append(s)
				  			 .append(System.lineSeparator()), (x,y) -> x.append(y));
		return messages;
	}
	

	protected void showExceptionDialog(Exception ex) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Ops! something went wrong");
		alert.setContentText("Application error");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();

		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
		
		
	}
	
	
	

}
