package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.Util;
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



	@Override
	public List<CheckoutRecord> allCheckoutRecords() {
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecord> retval = new ArrayList<>();
		retval.addAll(da.readCheckoutRecordMap().values());
		return retval;
	}


	public void checkoutBook(String memberId, String bookISBN) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mbrs = da.readMemberMap();
		HashMap<String, Book> bks = da.readBooksMap();

		List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();
		checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),bks.get(bks.keySet().toArray()[0]).getCopy(0)));
		checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),bks.get(bks.keySet().toArray()[0]).getCopy(1)));
		checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),bks.get(bks.keySet().toArray()[0]).getCopy(0)));
		da.saveCheckoutRecord(new CheckoutRecord(""+ Util.randomId(),mbrs.get(mbrs.keySet().toArray()[0]),checkoutRecordEntries));

	}
}
