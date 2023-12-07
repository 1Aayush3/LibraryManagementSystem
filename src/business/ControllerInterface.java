package business;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import validation.RuleException;

public interface ControllerInterface {
	public void login(String id, String password) throws RuleException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public HashMap<String, Book> allBooks();
	public Book searchBookByISBN(String isbn);

	public void addBookCopy(String isbn);
	public List<CheckoutRecord> allCheckoutRecords();
	public List<CheckoutRecord> filteredCheckoutRecords(LocalDate date, Integer limit);

	public void checkoutBook(String memberId, String bookISBN) throws RuleException;

}
