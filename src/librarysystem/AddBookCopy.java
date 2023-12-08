package librarysystem;

import business.Book;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddBookCopy extends JFrame {

    public static final AddBookCopy INSTANCE = new AddBookCopy();
    private JButton searchButton;
    private boolean isInitialized = false;
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
        if(this.isInitialized){
            AddBookCopy.INSTANCE.setVisible(true);
            return;
        }

        setupDefaultDesigns();
        initializeFields();
        createTopPanel();
        createTable();

        JPanel bottomPannel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("<= Back to Main");
        addBackButtonListener(backButton);
        bottomPannel.add(backButton);
        add(bottomPannel,BorderLayout.SOUTH);
        isInitialized();
        actionListeners();
    }

    private void setupDefaultDesigns() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        Util.centerFrameOnDesktop(AddBookCopy.INSTANCE);
        this.setVisible(true);
    }

    void isInitialized(){
        this.isInitialized = true;
    }


    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            AddBookCopy.INSTANCE.setVisible(false);
            Dashboard.INSTANCE.init();
        });
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
        panel.add(new JScrollPane(bookListTable), BorderLayout.CENTER);
        add(panel);
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
            searchResult.setText("The Book Title is: " + book.getTitle());
            addBookCopy.setVisible(true);
        }else{
            searchResult.setText("Sorry No data found, Please try again");
            addBookCopy.setVisible(false);
        }

    }



}
