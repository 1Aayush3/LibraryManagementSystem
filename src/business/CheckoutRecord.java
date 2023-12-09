package business;

import java.io.Serializable;
import java.util.List;

public class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = 6555690276685962829L;

    private String checkoutRecordId;
    private LibraryMember member;
    private List<CheckoutRecordEntry> checkoutRecordEntryList;

    public CheckoutRecord(String checkoutRecordId, LibraryMember member, List<CheckoutRecordEntry> checkoutRecordEntryList) {
        this.checkoutRecordId = checkoutRecordId;
        this.member = member;
        this.checkoutRecordEntryList = checkoutRecordEntryList;
    }

    public String getCheckoutRecordId() {
        return checkoutRecordId;
    }

    public void setCheckoutRecordId(String checkoutRecordId) {
        this.checkoutRecordId = checkoutRecordId;
    }

    public LibraryMember getMember() {
        return member;
    }

    public void setMember(LibraryMember member) {
        this.member = member;
    }

    public List<CheckoutRecordEntry> getCheckoutRecordEntryList() {
        return checkoutRecordEntryList;
    }

    public void setCheckoutRecordEntryList(List<CheckoutRecordEntry> checkoutRecordEntryList) {
        this.checkoutRecordEntryList = checkoutRecordEntryList;
    }
    public void addCheckoutRecordEntryList(CheckoutRecordEntry checkoutRecordEntry) {
        this.checkoutRecordEntryList.add(checkoutRecordEntry);
    }
    @Override
    public boolean equals(Object ob) {
        if(ob == null) return false;
        if(ob.getClass() != getClass()) return false;
        CheckoutRecord b = (CheckoutRecord) ob;
        return b.checkoutRecordId.equals(checkoutRecordId);
    }
    @Override
    public String toString() {
        return "member: " + member.getMemberId() + ", checkoutRecords: " + checkoutRecordEntryList.get(0).getCheckoutDate() ;
    }


}
