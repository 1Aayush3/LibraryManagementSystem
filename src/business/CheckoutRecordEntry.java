package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

    private static final long serialVersionUID = 6655690276685962829L;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BookCopy bookCopy;

    public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
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
