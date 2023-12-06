package validation;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
final public class RuleException extends Exception {
	public RuleException() {
		super();
	}
	public RuleException(String msg) {
		super(msg);
	}

	public RuleException(Component component, String msg){
		JOptionPane.showMessageDialog(component, msg);
	}
}
