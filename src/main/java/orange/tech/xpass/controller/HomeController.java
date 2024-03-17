package orange.tech.xpass.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import orange.tech.xpass.entity.Key;
import orange.tech.xpass.fx.ActionTableCell;
import orange.tech.xpass.fx.PasswordFieldTableCell;
import orange.tech.xpass.modal.OnModalAction;
import orange.tech.xpass.navigation.NavigationService;

@Component
public class HomeController extends BaseController  {

	@FXML
	private Accordion filter;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;

	@FXML
	private TableView<Key> keys;

	private NavigationService navigationService;
	
	private ApplicationContext ctx;
			
	public HomeController(NavigationService navigationService,ApplicationContext ctx) {
		this.navigationService = navigationService;		
		this.ctx = ctx;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ObservableList<Key> list = FXCollections.observableArrayList();

		list.add(new Key(1, LocalDate.now(), "Note 1", "user-1", "abc@123"));
		list.add(new Key(2, LocalDate.now(), "Note 2", "user-2", "xyz@123"));
		list.add(new Key(3, LocalDate.now(), "Note 3", "user-3", "ksakh"));
		list.add(new Key(4, LocalDate.now(), "Note 4", "user-4", "19sng"));

		TableColumn<Key, String> passwordColumn = new TableColumn<>("Password");
		passwordColumn.setCellFactory(c -> new PasswordFieldTableCell<>(canProceed -> {
			OnModalAction action = ctx.getBean(MainController.class);	
			action.canProceed(() -> canProceed.accept(true));
		}));
		passwordColumn.setCellValueFactory(c -> c.getValue().passwordProperty());

		TableColumn<Key, Void> editColumn = new TableColumn<>("Edit");
		editColumn.setCellFactory(c -> new ActionTableCell<>(FontAwesomeSolid.EDIT, key -> {
			navigation.set(navigationService.getNavigator(KeyController.class, () -> key));
		}));

		TableColumn<Key, Void> deleteColumn = new TableColumn<>("Delete");
		deleteColumn.setCellFactory(c -> new ActionTableCell<>(FontAwesomeSolid.TRASH, key -> {
			OnModalAction action = ctx.getBean(MainController.class);
			action.delete(key, () -> keys.getItems().remove(key));
		}));

		keys.setItems(list);
		keys.getColumns().addAll(List.of(passwordColumn, editColumn, deleteColumn));
		
		btnSearch.setOnAction(evt -> onSearchHandled());
		txtSearch.setOnAction(evt -> onSearchHandled());
		
		
	}

	private void onSearchHandled() {
		txtSearch.clear();
		System.out.println("search was done!");
	}

	

}
