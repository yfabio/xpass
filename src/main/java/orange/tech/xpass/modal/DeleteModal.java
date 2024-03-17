package orange.tech.xpass.modal;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import orange.tech.xpass.command.Command;
import orange.tech.xpass.controller.DeleteModalController;
import orange.tech.xpass.navigation.NavigationService;

@Component
public class DeleteModal implements Modal {
	
	private NavigationService navigationService;
	
	private DeleteModalController deleteModalController;
			
	public DeleteModal(NavigationService navigationService,DeleteModalController deleteModalController) {
		this.navigationService = navigationService;		
		this.deleteModalController = deleteModalController;
	}

	@Override
	public void open(Consumer<Boolean> con) {
		con.accept(true);
	}

	@Override
	public void close(Consumer<Boolean> con) {
		con.accept(false);
	}

	@Override
	public void show(Consumer<Node> con) {
		Pane root = navigationService.getNavigator(DeleteModalController.class).navigate();
		con.accept(root);
	}

	@Override
	public void done(Command cmd) {
		deleteModalController.ok.setOnAction(evt -> cmd.execute());		
	}

	@Override
	public void cancel(Command cmd) {
		deleteModalController.cancel.setOnAction(evt -> cmd.execute());		
	}

	
	
		
	

}
