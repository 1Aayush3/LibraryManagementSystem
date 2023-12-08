package validation;

import Utils.Message;
import librarysystem.AddBookWindow;
import librarysystem.AddLibraryMemberWindow;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBookRuleSet implements RuleSet {

    AddBookWindow AddBookWindow;
    @Override
    public void applyRules(Component ob) throws RuleException {
        AddBookWindow = (AddBookWindow) ob;
        final String[] values = {
                AddBookWindow.getIsbnTextField().getText(),
                (String) AddBookWindow.getAuthorComboBox().getSelectedItem(),
                AddBookWindow.getTitleTextField().getText(),
                AddBookWindow.getMaxCheckoutLengthTextField().getText(),
        };
        checkIdNullValidity(values);
    }

    private void checkIdNullValidity(String[] values) throws RuleException{
        for(final var value: values){
            if(value == null || value.trim().isEmpty()){
                throw new RuleException( Message.emptyFieldText);
            }
        }
    }
}
