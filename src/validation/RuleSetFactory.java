package validation;
import librarysystem.AddBookCopyWindow;
import librarysystem.CheckOutWindow;
import librarysystem.AddLibraryMemberWindow;
import librarysystem.LoginWindow;

import java.awt.Component;
import java.util.HashMap;

final public class RuleSetFactory {
    private RuleSetFactory(){}
    static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
    static {
        map.put(LoginWindow.class, new LoginRuleSet());
        map.put(CheckOutWindow.class, new CheckoutRuleSet());
        map.put(AddLibraryMemberWindow.class, new AddMemberRuleSet());
        map.put(AddBookCopyWindow.class, new AddBookCopyRuleSet());
    }
    public static RuleSet getRuleSet(Component c) {
        Class<? extends Component> cl = c.getClass();
        if(!map.containsKey(cl)) {
            throw new IllegalArgumentException(
                    "No RuleSet found for this Component");
        }
        return map.get(cl);
    }
}

