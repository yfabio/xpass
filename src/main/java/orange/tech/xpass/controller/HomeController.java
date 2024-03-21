package orange.tech.xpass.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.modelmapper.ModelMapper;
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
import orange.tech.xpass.fx.ActionTableCell;
import orange.tech.xpass.fx.PasswordFieldTableCell;
import orange.tech.xpass.modal.OnModalAction;
import orange.tech.xpass.navigation.NavigationService;
import orange.tech.xpass.property.Key;
import orange.tech.xpass.repository.KeyRepository;

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
	
	private KeyRepository keyRepository;
	
	private ModelMapper modelMapper;
			
	public HomeController(NavigationService navigationService,
			ApplicationContext ctx,KeyRepository keyRepository,
			ModelMapper modelMapper) {
		this.navigationService = navigationService;		
		this.ctx = ctx;
		this.keyRepository = keyRepository;		
		this.modelMapper = modelMapper;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		var col  = keyRepository.findAll();
		
		var mapped = col.stream().map(e -> modelMapper.map(e, Key.class)).toList();
											 		
		ObservableList<Key> list = FXCollections.observableArrayList(mapped);
		
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
		
		//TODO search in the database
		
	
		
			
	}

	

}
