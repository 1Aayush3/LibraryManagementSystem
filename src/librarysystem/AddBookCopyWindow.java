package librarysystem;

import business.Book;
import business.SystemController;
import validation.RuleException;
import validation.RuleSet;
import validation.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddBookCopyWindow extends JFrame {

    public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    private JButton searchButton;
    private boolean isInitialized = false;
    private JTextField searchField;
    private JPanel titlePanel;
    private JTable bookListTable;
    private JLabel searchResult;
    private JPanel bottomPannel;
    private JButton addBookCopy;
    private Object[][] rows;
    private String isbn;
    private List<String> columns;
    private JPanel panel;
    private SystemController systemController;
    private AddBookCopyWindow(){}
    void init(){
        if(this.isInitialized){
            AddBookCopyWindow.INSTANCE.setVisible(true);
            return;
        }

        setupDefaultDesigns();
        initializeFields();
        createTopPanel();
        createTable();
        createBottomPanel();

        add(bottomPannel,BorderLayout.SOUTH);
        isInitialized();
        actionListeners();
    }

    private void createBottomPanel() {
        bottomPannel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("<= Back to Main");
        addBackButtonListener(backButton);
        bottomPannel.add(backButton);
    }

    private void setupDefaultDesigns() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
        this.setVisible(true);
    }

    private void defineTitlePanel() {
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel title = new JLabel("Library Management System - Books");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 20));
        titlePanel.add(title);
    }

    void isInitialized(){
        this.isInitialized = true;
    }


    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            AddBookCopyWindow.INSTANCE.setVisible(false);
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
                updateTable();
            }
        });
    }

    private void updateTable() {
        DefaultTableModel tableModel = TableUtil.getDefaultTableModel(columns, rows);
        bookListTable.setModel(tableModel);
        bookListTable.revalidate();
        bookListTable.repaint();
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
        defineTitlePanel();
        JPanel titleAndTopPanel = new JPanel(new BorderLayout());
        titleAndTopPanel.add(titlePanel, BorderLayout.NORTH);
        titleAndTopPanel.add(topPanel, BorderLayout.CENTER);

        panel.add(titleAndTopPanel, BorderLayout.NORTH);
    }

    public String getIsbn(){
        return this.isbn;
    }

    private void performSearch() {
        isbn = searchField.getText();
        Book book = systemController.searchBookByISBN(isbn);
        RuleSet rules = RuleSetFactory.getRuleSet(this);
        try{
            rules.applyRules(this);
        }catch(RuleException e){
            searchResult.setText(e.getMessage());
            addBookCopy.setVisible(false);
            return;
        }

        if(book != null){
            searchResult.setText("The Book Title is: " + book.getTitle());
            addBookCopy.setVisible(true);
        }else{
            searchResult.setText("Sorry No data found, Please try again");
            addBookCopy.setVisible(false);
        }
    }
}
