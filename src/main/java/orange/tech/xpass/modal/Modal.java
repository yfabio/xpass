package orange.tech.xpass.modal;

import java.util.function.Consumer;

import javafx.scene.Node;
import orange.tech.xpass.command.Command;


public interface Modal {	
	void open(Consumer<Boolean> con);
	void close(Consumer<Boolean> con);
	void show(Consumer<Node> con);
	void done(Command cmd);
	void cancel(Command cmd);
}
