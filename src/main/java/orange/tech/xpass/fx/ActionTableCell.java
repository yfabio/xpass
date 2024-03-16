package orange.tech.xpass.fx;

import java.util.function.Consumer;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;

public class ActionTableCell<S> extends TableCell<S, Void>{
		
	private Button button = new Button();
	
	private AnchorPane anchorPane = new AnchorPane(button);
	
	private Consumer<S> command;
	
	private Ikon icon;
	
	public ActionTableCell() {}
			
	public ActionTableCell(Ikon icon, Consumer<S> command) {
		this.command = command;
		this.icon = icon;
		button.setOnAction(evt -> this.command.accept(getTableRow().getItem()));	
		setAlignment(Pos.CENTER);
		
		button.getStyleClass().add("home_table_action_button");		
		AnchorPane.setTopAnchor(button, 0.0);
		AnchorPane.setBottomAnchor(button, 0.0);
		AnchorPane.setRightAnchor(button, 0.0);
		AnchorPane.setLeftAnchor(button, 0.0);
	}

	@Override
	protected void updateItem(Void item, boolean empty) {
		setGraphic(null);
		if(!empty) {			
			try {				
				FontIcon fontIcon = new FontIcon(icon);
				fontIcon.setIconSize(16);
				button.setGraphic(fontIcon);				
				setGraphic(anchorPane);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
		
}
