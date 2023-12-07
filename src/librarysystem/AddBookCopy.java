package librarysystem;

import business.Book;
import business.SystemController;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBookCopy extends JFrame {

    public static final AddBookCopy INSTANCE = new AddBookCopy();
    private JButton searchButton;
    private JTextField searchField;
    private JTable bookListTable;
    private JLabel searchResult;
    private JButton addBookCopy;
    private Object[][] rows;
    private String isbn;
    private List<String> columns;
    private JPanel panel;
    private SystemController systemController;
    private AddBookCopy(){}
    void init(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660,500);
        this.setVisible(true);

        initializeFields();
        createTopPanel();

        createTable();
        panel.add(new JScrollPane(bookListTable), BorderLayout.CENTER);
        add(panel);
        actionListeners();
    }

    private void actionListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        addBookCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemController.addBookCopy(isbn);
                rows = TableUtil.getRowsBooks(systemController.allBooks().values().stream().toList());
                DefaultTableModel tableModel = TableUtil.getDefaultTableModel(columns, rows);
                bookListTable.setModel(tableModel);
                bookListTable.revalidate();
                bookListTable.repaint();
            }
        });
    }

    private void createTable() {
        columns = new ArrayList<>();
        columns.add("ISBN");
        columns.add("Title");
        columns.add("Copy Number");
        systemController = new SystemController();

        rows = TableUtil.getRowsBooks(systemController.allBooks().values().stream().toList());
        DefaultTableModel tableModel = TableUtil.getDefaultTableModel(columns, rows);
        bookListTable = new JTable(tableModel);
    }

    private void initializeFields() {
        searchField = new JTextField(20);
        searchResult = new JLabel();
        addBookCopy = new JButton("Add Book Copy");
        searchButton = new JButton("Search");
        addBookCopy.setVisible(false);
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

        JLabel searchText = new JLabel("Search Book by ISBN");
        searchText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.add(searchField);
        searchFieldPanel.add(searchButton);

        searchPanel.add(searchText, BorderLayout.NORTH);
        searchPanel.add(searchFieldPanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel();
        resultPanel.add(searchResult);
        resultPanel.add(addBookCopy);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(resultPanel, BorderLayout.SOUTH);

         panel = new JPanel(new BorderLayout());
        panel.add(topPanel, BorderLayout.NORTH);
    }


    private void performSearch() {
        isbn = searchField.getText();

        Book book = systemController.searchBookByISBN(isbn);

        if(book != null){
            searchResult.setText("Are you Searching for: " + book.getTitle());
            addBookCopy.setVisible(true);
        }else{
            searchResult.setText("Sorry No data found, Please try again");
            addBookCopy.setVisible(false);
        }

    }



}
