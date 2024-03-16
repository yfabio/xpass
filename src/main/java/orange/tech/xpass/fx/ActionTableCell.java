package orange.tech.xpass.fx;

import java.util.function.Consumer;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ActionTableCell<S> extends TableCell<S, Void>{
		
	private Button button = new Button();
	
	private Consumer<S> command;
	
	private FontIcon icon;
	
	public ActionTableCell() {}
			
	public ActionTableCell(FontIcon icon, Consumer<S> command) {
		this.command = command;
		this.icon = icon;
		button.setOnAction(evt -> this.command.accept(getTableRow().getItem()));	
		setAlignment(Pos.CENTER);
	}

	@Override
	protected void updateItem(Void item, boolean empty) {
		setGraphic(null);
		if(!empty) {			
			try {				
				FontIcon fontIcon = new FontIcon("");
				button.setGraphic(fontIcon);							
				setGraphic(button);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
		
}
