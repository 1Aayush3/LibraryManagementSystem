package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import validation.RuleException;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	private DataAccess da = new DataAccessFacade();
	
	public void login(String id, String password) throws RuleException {
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new RuleException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new RuleException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public HashMap<String, Book> allBooks() {
		return da.readBooksMap();
	}

	@Override
	public Book searchBookByISBN(String isbn) {
		da.getBookByIsbn(isbn);
		return da.getBookByIsbn(isbn);
	}

	@Override
	public void addBookCopy(String isbn) {
		HashMap<String, Book> bookList = da.readBooksMap();
		Book book = bookList.get(isbn);
		book.addCopy();
		da.updateBook(book);
	}


}
