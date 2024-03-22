package orange.tech.xpass.modal;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import orange.tech.xpass.command.Command;
import orange.tech.xpass.controller.ProceedModalController;
import orange.tech.xpass.navigation.NavigationService;

@Component
public class ProceedModal implements Modal {
	
	private NavigationService navigationService;
	
	private ProceedModalController proceedModalController;
	
	public ProceedModal(NavigationService navigationService,
			ProceedModalController proceedModalController) {
		this.navigationService = navigationService;
		this.proceedModalController = proceedModalController;
		
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
		Pane root = navigationService.getNavigator(ProceedModalController.class).navigate();
		con.accept(root);
	}

	@Override
	public void done(Command cmd) {
		proceedModalController.ok.setOnAction(evt -> onPasswordMatch(cmd));
		proceedModalController.password.setOnAction(evt -> onPasswordMatch(cmd));
	}

	private void onPasswordMatch(Command cmd) {
		if(proceedModalController.doesPasswordMatch()) {
			cmd.execute();
		}
	}

	@Override
	public void cancel(Command cmd) {
		proceedModalController.cancel.setOnAction(evt -> cmd.execute());
	}

	
}
