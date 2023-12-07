package validation;

import Utils.Message;
import librarysystem.AddLibraryMemberWindow;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMemberRuleSet implements RuleSet {

    AddLibraryMemberWindow libraryMember;
    @Override
    public void applyRules(Component ob) throws RuleException {
        libraryMember = (AddLibraryMemberWindow) ob;
        final String[] values = {
                libraryMember.getMemberIdTextField().getText(),
                libraryMember.getFirstNameTextField().getText(),
                libraryMember.getLastNameTextField().getText(),
                libraryMember.getPhoneNumberTextField().getText(),
                libraryMember.getCityTextField().getText(),
                libraryMember.getStateTextField().getText(),
                libraryMember.getStreetTextField().getText(),
                libraryMember.getZipCodeTextField().getText()
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
