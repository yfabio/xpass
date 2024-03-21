package orange.tech.xpass.fx;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class TextFieldTableCellFactory <S,T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
	
	@Override
	public TableCell<S, T> call(TableColumn<S, T> arg) {
		 TextFieldTableCell<S,T> tableCell = new TextFieldTableCell<S,T>();	 		 
		 tableCell.setAlignment(Pos.CENTER);		 
		 return tableCell;
	}

}
