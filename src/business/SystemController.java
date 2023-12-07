package business;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import validation.RuleException;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws RuleException {
		DataAccess da = new DataAccessFacade();
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
	public void addLibraryMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	public static String generateNewMemberId() {
		List<String> allMemberIds = new SystemController().allMemberIds();
		String latestMemberId = getLatestMemberId(allMemberIds);
		return generateNextMemberId(latestMemberId);
	}

	private static String getLatestMemberId(List<String> memberIds) {
		// If the list is empty, return a default starting member ID
		if (memberIds.isEmpty()) {
			return "1000";
		}

		// Find the maximum member ID from the list
		return memberIds.stream()
				.max(Comparator.comparingInt(Integer::parseInt))
				.orElse("1000");
	}

	private static String generateNextMemberId(String latestMemberId) {
		// Increment the latest member ID by 1
		int nextId = Integer.parseInt(latestMemberId) + 1;
		return String.valueOf(nextId);
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public Book searchBookByISBN(String isbn) {
		DataAccess da = new DataAccessFacade();
		da.getBookByIsbn(isbn);
		return da.getBookByIsbn(isbn);
	}

	@Override
	public void addBookCopy(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookList = da.readBooksMap();
		Book book = bookList.get(isbn);
		book.addCopy();
		da.updateBook(book);
	}
}
