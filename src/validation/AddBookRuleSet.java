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
        checkNumericValidity(values[3]);
    }


    private void checkNumericValidity(String values) throws RuleException {
        try {
            // Attempt to parse the string as an integer
            int integerValue = Integer.parseInt(values);

            // If no exception is thrown, the string is a valid integer
            System.out.println("Valid Integer: " + integerValue);
        } catch (NumberFormatException e) {
            // If a NumberFormatException is caught, the string is not a valid integer
            throw new RuleException("Checkout Length should be numeric");
        }
    }

    private void checkIdNullValidity(String[] values) throws RuleException{
        for(final var value: values){
            if(value == null || value.trim().isEmpty()){
                throw new RuleException( Message.emptyFieldText);
            }
        }
    }
}
