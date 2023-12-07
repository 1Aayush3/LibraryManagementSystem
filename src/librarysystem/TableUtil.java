package librarysystem;

import business.Book;
import business.CheckoutRecord;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        Object[][] models = new Object[][];
        // Populate the table model
        for (int i =0;i<checkoutRecords.size();i++) {
            CheckoutRecord object = checkoutRecords.get(i);
            models[i] = new Object[] {
                    object.getMember().getFirstName(),
                    object.getMember().getLastName(),
                    object.getCheckoutRecordId(),
            };
        }
        return models;
    }
    public static Object[][] getRowsBooks(List<Book> objects) {
        Object[][] models = new Object[][];
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
}
