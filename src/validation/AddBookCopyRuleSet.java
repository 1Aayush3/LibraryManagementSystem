package validation;

import librarysystem.AddBookCopy;
import librarysystem.LoginWindow;

import java.awt.*;

public class AddBookCopyRuleSet implements RuleSet{
    AddBookCopy addBookCopy;
    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookCopy = (AddBookCopy) ob;
        checkNullException();
    }

    private void checkNullException() throws RuleException {
        if(addBookCopy.getIsbn().isEmpty()){
            throw new RuleException("Isbn can't be empty");
        }
    }

}
