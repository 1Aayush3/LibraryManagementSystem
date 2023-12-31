package business;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
	public void addLibraryMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}

	@Override
	public void addBook(Book book){
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}

	@Override
	public HashMap<String, LibraryMember> allMembers() {
		List<String> retval = new ArrayList<>();

		return da.readMemberMap();
	}

	@Override
	public List<String> allMemberIds() {
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
		Collections.sort(retval, Comparator.comparing(o -> o.getCheckoutRecordEntryList().get(0).getCheckoutDate()));
		return retval;
	}
	@Override
	public List<CheckoutRecord> filteredCheckoutRecords(LocalDateTime date, Integer limit){
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecord> retval = new ArrayList<>();
		retval.addAll(da.readCheckoutRecordMap().values());
		Collections.sort(retval, Comparator.comparing(o -> o.getCheckoutRecordEntryList().get(0).getCheckoutDate()));

		if(date != null ) {
			retval = retval.stream()
					.filter(order -> order.getCheckoutRecordEntryList().get(0).getCheckoutDate().isAfter(date))
					.collect(Collectors.toList());
		}

		return limit == null? retval : retval.subList(0, Math.min(limit,retval.size()));
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
		Book book = availableCopy.getBook();


		CheckoutRecordEntry checkoutEntry = new CheckoutRecordEntry(LocalDateTime.now(), LocalDateTime.now().plusDays(book.getMaxCheckoutLength()), availableCopy);

		CheckoutRecord checkoutRecord = getCheckoutRecordByMemberId(memberId);

		if(checkoutRecord == null) {
			List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();
			checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDateTime.now(), LocalDateTime.now().plusDays(book.getMaxCheckoutLength()),availableCopy));
			checkoutRecord = new CheckoutRecord("" + Util.randomId(), mbrs.get(memberId), checkoutRecordEntries);
			da.saveCheckoutRecord(checkoutRecord);
		}
		else{
			checkoutRecord.addCheckoutRecordEntryList(checkoutEntry);
			da.saveCheckoutRecord(checkoutRecord);
		}

		availableCopy.changeAvailability();
		availableCopy.getBook().updateCopies(availableCopy);
		da.updateBook(availableCopy.getBook());
	}

	private CheckoutRecord getCheckoutRecordByMemberId(String memberId) {
		List<CheckoutRecord> allCheckoutRecords = allCheckoutRecords();
		Optional<CheckoutRecord> record
				= allCheckoutRecords.stream()
				.filter(r -> r.getMember().getMemberId().equals(memberId)).findFirst();
		return record.isEmpty()? null : record.get();
	}

	@Override
	public List<Author> getAllAuthors(){ return new DataAccessFacade().getAllAuthors();}
	@Override
	public String getTotalLibraryMemberss() {
		return String.valueOf(allMemberIds().size());
	}

	@Override
	public String getTotalCheckedOut() {
		return String.valueOf(allCheckoutRecords().size());
	}

	@Override
	public String getTotalBooks() {
		return String.valueOf(allBooks().size());
	}

	@Override
	public String getStringBetweenBrackets(String selectedItem){
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		Matcher matcher = pattern.matcher(selectedItem);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";

	}

}
