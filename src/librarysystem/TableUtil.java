package librarysystem;

import business.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableUtil {

    public static void updateTableData(JTable table, List<String> columnNames,String[][] rows) {

        DefaultTableModel tableModel = getDefaultTableModel(columnNames,rows);
        table.setModel(tableModel);
        table.revalidate();
        table.repaint();
    }

    public static DefaultTableModel getDefaultTableModel(List<String> columnNames,Object[][] rows) {
        ControllerInterface ci = new SystemController();
        List<CheckoutRecord> checkoutRecords = ci.allCheckoutRecords();

        DefaultTableModel tableModel = new DefaultTableModel();
        for(String col : columnNames){
            tableModel.addColumn(col);
        }

        // Populate the table model
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
        return tableModel;
    }

    public static Object[][] getRowsCheckout(List<CheckoutRecord> checkoutRecords) {

        Object[][] models = new Object[checkoutRecords.size()][];
        // Populate the table model
        for (int i =0;i<checkoutRecords.size();i++) {
            CheckoutRecord object = checkoutRecords.get(i);
            for(CheckoutRecordEntry checkoutrecordentry :object.getCheckoutRecordEntryList()) {
                Book book = checkoutrecordentry.getBookCopy().getBook();
                LocalDateTime checkoutDate = checkoutrecordentry.getCheckoutDate();
                LocalDateTime dueDate = checkoutrecordentry.getDueDate();
                models[i] = new Object[]{
                        object.getMember().getFullName(),
                        book.getTitle(),
                        book.getIsbn(),
                        checkoutDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                };
            }
        }
        return models;
    }
    public static Object[][] getRowsBooks(List<Book> objects) {
        Object[][] models = new Object[objects.size()][];
        // Populate the table model
        for (int i =0;i<objects.size();i++) {
            Book object = objects.get(i);
            models[i] = new Object[] {
                    object.getIsbn(),
                    object.getTitle(),
                    object.getCopyNums(),
            };
        }
        return models;
    }

    public static Object[][] getRowsMembers(HashMap<String, LibraryMember> objects) {
        Object[][] models = new Object[objects.size()][];
        // Populate the table model
        for (int i =0;i<objects.size();i++) {
            String key = (String)objects.keySet().toArray()[i];
            LibraryMember object =  objects.get(key);
            models[i] = new Object[] {
                    object.getMemberId(),
                    object.getFullName(),
                    object.getTelephone(),
                    object.getAddress(),
            };
        }
        return models;
    }

    public static List<String> getColumnsCheckout() {
        List<String> columns = new ArrayList<>();
        columns.add("Full Name");
        columns.add("Book");
        columns.add("ISBN");
        columns.add("Checkout Date");
        columns.add("Due Date");
        return columns;
    }
    public static List<String> getColumnsMembers() {
        List<String> columns = new ArrayList<>();
        columns.add("Member Id");
        columns.add("Full Name");
        columns.add("Telephone");
        columns.add("Address");
        return columns;
    }

}
