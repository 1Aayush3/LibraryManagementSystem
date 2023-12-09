package validation;

import librarysystem.AddBookCopyWindow;

import java.awt.*;

public class AddBookCopyRuleSet implements RuleSet{
    AddBookCopyWindow addBookCopy;
    @Override
    public void applyRules(Component ob) throws RuleException {
        addBookCopy = (AddBookCopyWindow) ob;
        checkNullException();
    }

    private void checkNullException() throws RuleException {
        if(addBookCopy.getIsbn().isEmpty()){
            throw new RuleException("Isbn can't be empty");
        }
    }

}
