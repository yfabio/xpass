package orange.tech.xpass;

import org.springframework.context.ApplicationEvent;

import javafx.stage.Stage;

public class StageReadyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public StageReadyEvent(Object source) {
		super(source);		
	}

	public Stage getStage() {
		return Stage.class.cast(getSource());
	}

}