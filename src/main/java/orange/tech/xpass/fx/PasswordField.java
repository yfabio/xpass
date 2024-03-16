package orange.tech.xpass.fx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

public class PasswordField extends javafx.scene.control.PasswordField {

	private BooleanProperty hide = new SimpleBooleanProperty(false);
	
	public PasswordField() {
		setSkin(new PasswordFieldSkin(this));		
	}
			
	private class PasswordFieldSkin extends TextFieldSkin {

		public PasswordFieldSkin(TextField control) {
			super(control);
		}

		@Override
		protected String maskText(String txt) {
			if (hide.get()) {
				return txt;
			} else {
				return super.maskText(txt);
			}			
		}
	}
	
	public void toggle() {			
		hide.set(!hide.get());	
		setText(getText());		
	}
	
	public BooleanProperty hideProperty() {
		return hide;
	}

}
