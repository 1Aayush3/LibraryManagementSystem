package validation;

import Utils.Message;
import librarysystem.LoginWindow;

import javax.swing.*;
import java.awt.*;

public class LoginRuleSet implements RuleSet {

    LoginWindow loginWindow;
    @Override
    public void applyRules(Component ob) throws RuleException {
        loginWindow = (LoginWindow) ob;
        checkIdNullValidity();
        checkIdIntegerValidity();
    }

    private void checkIdNullValidity() throws RuleException{
        if(loginWindow.getId().isEmpty() || loginWindow.getPassword().isEmpty()){
            throw new RuleException(loginWindow, Message.invalidErrorText);
        }
    }
    private void checkIdIntegerValidity() throws RuleException {
        String userId = loginWindow.getId();
        try {
            Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new RuleException(loginWindow, Message.invalidErrorText);
        }
    }
}
