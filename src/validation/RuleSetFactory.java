package validation;
import librarysystem.AddLibraryMemberWindow;
import librarysystem.LoginWindow;

import java.awt.Component;
import java.util.HashMap;

final public class RuleSetFactory {
    private RuleSetFactory(){}
    static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
    static {
        map.put(LoginWindow.class, new LoginRuleSet());
        map.put(AddLibraryMemberWindow.class, new AddMemberRuleSet());
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

