package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import validation.RuleException;

public interface ControllerInterface {
	public void login(String id, String password) throws RuleException;
	public List<String> allMemberIds();
	public List<String> allBookIds();

	public Book searchBookByISBN(String isbn);

	public void addBookCopy(String isbn);
}
