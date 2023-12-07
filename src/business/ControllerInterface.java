package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import validation.RuleException;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<CheckoutRecord> allCheckoutRecords();
	public void checkoutBook(String memberId, String bookISBN) throws RuleException;
	
}
