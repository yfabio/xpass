package orange.tech.xpass.modal;


import orange.tech.xpass.command.Command;
import orange.tech.xpass.entity.Key;

public interface OnModalAction {
	void delete(Key key, Command cmd);	
	void canProceed(Command cmd);
}
