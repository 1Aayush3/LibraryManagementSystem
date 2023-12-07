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
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public List<CheckoutRecord> allCheckoutRecords() {
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecord> retval = new ArrayList<>();
		retval.addAll(da.readCheckoutRecordMap().values());
		return retval;
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

	}
}
