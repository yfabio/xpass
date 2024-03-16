package orange.tech.xpass.fx;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;


public class PasswordFieldTableCellFactory<S,T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
		
	private class PasswordFieldTableCell extends TableCell<S, T> {
		
		private PasswordField password = new PasswordField();
		
		public PasswordFieldTableCell() {
			this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			this.setGraphic(null);
			this.password.getStyleClass().add("home_table_password_field");
			this.password.setEditable(false);
			
			MenuItem menuItemShow = new MenuItem("Show");
			
			menuItemShow.setOnAction(evt -> {
				
				//TODO open modal to request password;
				menuItemShow.setText("Hide");
				password.toggle();
				password.hideProperty().addListener((obs,x,y) -> {
					if(y) {
						menuItemShow.setText("Hide");		
					}else {						
						menuItemShow.setText("Show");		
					}
				});
				
				
			});
			
			MenuItem menuItemCopy = new MenuItem("copy");
			
			ContextMenu contextMenu = new ContextMenu();
			contextMenu.setAutoHide(true);	
			contextMenu.setAutoFix(true);
			contextMenu.setConsumeAutoHidingEvents(true);
			contextMenu.setHideOnEscape(true);
			
			contextMenu.getItems().addAll(menuItemShow,menuItemCopy);
			
			this.password.setContextMenu(contextMenu);
			
		}

		@Override
		protected void updateItem(T item, boolean empty) {
			super.updateItem(item, empty);
			if(!empty) {
				password.setText(String.class.cast(item));
				setGraphic(password);
			}else {
				setGraphic(null);
			}
		}
		
	}
	
	
	
	@Override
	public TableCell<S, T> call(TableColumn<S, T> arg0) {
		return new PasswordFieldTableCell();
	}

}
