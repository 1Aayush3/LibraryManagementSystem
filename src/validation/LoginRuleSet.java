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
    }

    private void checkIdNullValidity() throws RuleException{
        if(loginWindow.getId().isEmpty()){
            throw new RuleException(loginWindow, Message.blankUserIDErrorText);
        }
    }
}
