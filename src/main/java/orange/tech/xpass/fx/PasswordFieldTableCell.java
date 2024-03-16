package orange.tech.xpass.fx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;

public class PasswordFieldTableCell<S, T> extends TableCell<S, T> {
	
	private PasswordField password = new PasswordField();
	
	private ScheduledExecutorService job = Executors.newSingleThreadScheduledExecutor();

	public PasswordFieldTableCell() {
		this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		this.password.getStyleClass().add("home_table_password_field");
		this.password.setEditable(false);
		
		ContextMenu contextMenu = new ContextMenu();
		
		MenuItem menuItemShow = new MenuItem("show");
		
		menuItemShow.setOnAction(evt -> {
			
			//TODO open modal to request password;
			
			menuItemShow.setText("hide");
			password.toggle();
			password.hideProperty().addListener((obs,x,y) -> {
				if(y) {
					menuItemShow.setText("hide");		
				}else {						
					menuItemShow.setText("show");		
				}
			});
			
			
		});
		
		MenuItem menuItemCopy = new MenuItem("copy");
		
		

		menuItemCopy.setOnAction(evt -> {
			
			//TODO Modal to request password.
			
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putString(password.getText());
			clipboard.setContent(content);
			
			
			Tooltip toolTip = new Tooltip("Copied!");
			toolTip.setTextAlignment(TextAlignment.RIGHT);
			
										
			password.setTooltip(toolTip);
			
			Scene scene = password.getScene();
			Window window = password.getScene().getWindow();			
			Point2D coordination = password.localToScene(0.0, 0.0);			
			Double x = coordination.getX();
			Double y = coordination.getY();			
			x += scene.getX() + window.getX() + 62;
			y += scene.getY() + window.getY();						
			
			
			toolTip.show(password,x,y);			
			job.schedule(() -> {
				Platform.runLater(() -> toolTip.hide());
			},1,TimeUnit.SECONDS);
			job.shutdown();
			
		});
	
		
		
		contextMenu.setAutoHide(true);	
		contextMenu.setAutoFix(true);
		contextMenu.setConsumeAutoHidingEvents(true);
		contextMenu.setHideOnEscape(true);
		
		contextMenu.getItems().addAll(menuItemShow,menuItemCopy);
		
		this.password.setContextMenu(contextMenu);
		
	}

	@Override
	protected void updateItem(T item, boolean empty) {		
		if (!empty) {
			password.setText(String.class.cast(item));
			setGraphic(password);
		} else {
			setGraphic(null);
		}
	}

}
