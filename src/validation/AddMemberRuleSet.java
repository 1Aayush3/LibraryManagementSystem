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
        checkAlphaValidity(values);
        checkPhoneNumberValidity(values[3]);
        checkZipCodeValidity(values[7]);
    }

    private void checkAlphaValidity(String[] values) throws RuleException {
        // Firstname and lastname shouldn't have any special characters or numbers
        for (String value : new String[]{values[1], values[2]}) {
            if (!value.matches("[a-zA-Z]+")) {
                throw new RuleException("Name should only contain alphabets");
            }
        }
    }
    private void checkPhoneNumberValidity(String phoneNumber) throws RuleException {
        // Phone number should be 10 digits
        if (!phoneNumber.matches("\\d{10}")) {
            throw new RuleException("Phone number should be 10 digit");
        }
    }
    private void checkZipCodeValidity(String zipCode) throws RuleException {
        // Zip code should be a number less than 8 digits
        try {
            int zipCodeValue = Integer.parseInt(zipCode);
            if (zipCode.length() > 8) {
                throw new RuleException("Zip code can't be more than 8");
            }
        } catch (NumberFormatException e) {
            throw new RuleException("Zip code should be numeric");
        }
    }

    private void checkIdNullValidity(String[] values) throws RuleException{
        for(final var value: values){
            if(value == null || value.trim().isEmpty()){
                throw new RuleException( "All the fields are mandatory");
            }


        }
    }
}
