package dataaccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.*;
import dataaccess.DataAccessFacade.StorageType;


public class DataAccessFacade implements DataAccess {
	
	enum StorageType {
		BOOKS, MEMBERS, USERS, CHECKOUT;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			+ "\\src\\dataaccess\\storage";
	public static final String OUTPUT_DIR_MAC = System.getProperty("user.dir")
			+ "/src/dataaccess/storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	//implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	@Override
	public void saveNewBook(Book book) {
		HashMap<String, Book> bookList = readBooksMap();
		String isbn = book.getIsbn();
		bookList.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookList);
	}

	public void saveCheckoutRecord(CheckoutRecord checkoutRecord) {
		HashMap<String, CheckoutRecord> checkouts = readCheckoutRecordMap();
		String checkoutRecordId = checkoutRecord.getCheckoutRecordId();
		if(checkouts.containsKey(checkoutRecordId)){
			checkouts.remove(checkoutRecordId);
		}
		checkouts.put(checkoutRecordId, checkoutRecord);
		saveToStorage(StorageType.CHECKOUT, checkouts);
	}

	public void saveBook(Book book) {
		Map<String, Book> books = readBooksMap();
		String isbn = book.getIsbn();
		books.put(isbn, book);
		saveToStorage(StorageType.BOOKS, books);
	}
	

	@Override
	public void updateBook(Book book) {
		HashMap<String, Book> bookList = readBooksMap();
		bookList.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, bookList);
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		HashMap<String, Book> bookList = readBooksMap();
		return bookList.get(isbn);
	}

	@SuppressWarnings("unchecked")
	public  HashMap<String,Book> readBooksMap() {
		//Returns a Map with name/value pairs being
		//   isbn -> Book
		return (HashMap<String,Book>) readFromStorage(StorageType.BOOKS);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		//Returns a Map with name/value pairs being
		//   memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
	}


	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		//Returns a Map with name/value pairs being
		//   userId -> User
		return (HashMap<String, User>)readFromStorage(StorageType.USERS);
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap() {
		//Returns a Map with name/value pairs being
		//   checkoutRecordId -> CheckoutRecord
		return (HashMap<String, CheckoutRecord>)readFromStorage(StorageType.CHECKOUT);
	}

	public List<Author> getAllAuthors() {
		return new TestData().allAuthors;
	}
	
	
	/////load methods - these place test data into the storage area
	///// - used just once at startup  
	
		
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}
	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}
	static void loadCheckoutRecordMap(List<CheckoutRecord> checkoutRecords) {
		HashMap<String, CheckoutRecord> checkoutRecordHashMap = new HashMap<String, CheckoutRecord>();
		checkoutRecords.forEach(member -> checkoutRecordHashMap.put(member.getCheckoutRecordId(), member));
		saveToStorage(StorageType.CHECKOUT, checkoutRecordHashMap);
	}
	
	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			String osName = System.getProperty("os.name");
			boolean isMac = osName.startsWith("Mac");
			Path path = FileSystems.getDefault().getPath(isMac?OUTPUT_DIR_MAC:OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception e) {}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			String osName = System.getProperty("os.name");
			boolean isMac = osName.startsWith("Mac");
			Path path = FileSystems.getDefault().getPath(isMac?OUTPUT_DIR_MAC:OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(Exception e) {}
			}
		}
		return retVal;
	}
	
	
	
	final static class Pair<S,T> implements Serializable{
		
		S first;
		T second;
		Pair(S s, T t) {
			first = s;
			second = t;
		}
		@Override 
		public boolean equals(Object ob) {
			if(ob == null) return false;
			if(this == ob) return true;
			if(ob.getClass() != getClass()) return false;
			@SuppressWarnings("unchecked")
			Pair<S,T> p = (Pair<S,T>)ob;
			return p.first.equals(first) && p.second.equals(second);
		}
		
		@Override 
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}
		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}
		private static final long serialVersionUID = 5399827794066637059L;
	}
	
}
