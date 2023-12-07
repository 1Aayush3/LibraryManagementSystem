package validation;

import Utils.Message;
import business.*;
import librarysystem.CheckOutWindow;
import java.awt.*;


public class CheckoutRuleSet implements RuleSet {

    CheckOutWindow checkOutWindow;
    private SystemController ci;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkOutWindow = (CheckOutWindow) ob;
        ci = new SystemController();
        checkIdNullValidity();
    }

    private void checkIdNullValidity() throws RuleException{
        String memberId = checkOutWindow.getMemberId();
        String bookISBN = checkOutWindow.getBookISBN();
        if(memberId.isEmpty() || bookISBN.isEmpty()){
            throw new RuleException(checkOutWindow, Message.blankMemberIDErrorText);
        }
        try {
            ci.checkoutBook(memberId, bookISBN);
        }
        catch (Exception ex){
            throw new RuleException(checkOutWindow, ex.getMessage());
        }

        checkOutWindow.refreshTable();
    }
}
