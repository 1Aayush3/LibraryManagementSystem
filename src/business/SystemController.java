package business;

import java.time.LocalDate;
import java.util.*;

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
	@Override
	public List<CheckoutRecord> filteredCheckoutRecords(LocalDate date, Integer limit){
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecord> retval = new ArrayList<>();
		retval.addAll(da.readCheckoutRecordMap().values());
		Collections.sort(retval, Comparator.comparing(o -> o.getCheckoutRecordEntryList().get(0).getDueDate()));
		return limit == null? retval : retval.subList(0,limit);
	}


	public void checkoutBook(String memberId, String bookISBN) throws RuleException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mbrs = da.readMemberMap();
		if(!mbrs.containsKey(memberId)){
			throw new RuleException("Member not found");
		}
		HashMap<String, Book> bks = da.readBooksMap();
		if(!bks.containsKey(bookISBN)){
			throw new RuleException("Book not found with ISBN - "+ bookISBN);
		}
		BookCopy availableCopy = bks.get(bookISBN).getNextAvailableCopy();
		if(availableCopy == null){
			throw new RuleException("No available for book copy for "+ bookISBN);
		}

		List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();

		checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),availableCopy));
		da.saveCheckoutRecord(new CheckoutRecord(""+ Util.randomId(),mbrs.get(memberId),checkoutRecordEntries));

		availableCopy.changeAvailability();
		availableCopy.getBook().updateCopies(availableCopy);
		da.updateBook(availableCopy.getBook());
	}
}
