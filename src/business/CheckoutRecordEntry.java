package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CheckoutRecordEntry implements Serializable {

    private static final long serialVersionUID = 6655690276685962829L;
    private LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private BookCopy bookCopy;

    public CheckoutRecordEntry(LocalDateTime checkoutDate, LocalDateTime dueDate, BookCopy bookCopy) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    @Override
    public boolean equals(Object ob) {
        if(ob == null) return false;
        if(ob.getClass() != getClass()) return false;
        CheckoutRecordEntry b = (CheckoutRecordEntry) ob;
        return b.checkoutDate.equals(checkoutDate) && b.bookCopy.equals(bookCopy);
    }
}
