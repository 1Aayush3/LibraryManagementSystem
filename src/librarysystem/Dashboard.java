package librarysystem;

import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Dashboard extends JFrame {

    public static final Dashboard INSTANCE = new Dashboard();

    private JPanel infoPanel;
    private JPanel lowerPanel;
    public JTable table;
    private SystemController systemController;
    private boolean isInitialized = false;

    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
    void init(){
        if(this.isInitialized){
            Dashboard.INSTANCE.setVisible(true);
            refreshTable();
            return;
        }
        isInitialized(true);
        systemController = new SystemController();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMaximumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));

        this.setVisible(true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JLabel title = new JLabel("Library Management System");
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 20));
        topPanel.add(title);

        createInfoPanel();

        createTable();

        JPanel tableLabel = new JPanel(new BorderLayout());
        JLabel checkoutBooks = new JLabel("Books Checked out Today!");
        checkoutBooks.setFont(new Font(checkoutBooks.getFont().getFontName(), Font.BOLD, checkoutBooks.getFont().getSize()));

        tableLabel.add(checkoutBooks);
        tableLabel.setBorder(new EmptyBorder(0,40,0,20));

        lowerPanel.setBorder(new EmptyBorder(0,10,0,10));

        mainPanel.add(topPanel);
        mainPanel.add(infoPanel);
        mainPanel.add(tableLabel);
        mainPanel.add(lowerPanel);

        JButton backButton = new JButton("Logout");
        addBackButtonListener(backButton);
        mainPanel.add(backButton);

        mainPanel.setSize(660,500);
        mainPanel.setMaximumSize(new Dimension( 660,500));
        Util.centerFrameOnDesktop(Dashboard.INSTANCE);
        // Set the main panel as the content pane
        add(mainPanel);

        setVisible(true);

    }
    private DefaultTableModel getDefaultTableModel() {
        List<String> columns = TableUtil.getColumnsCheckout();

        Object[][] rows = TableUtil.getRowsCheckout(
                systemController.filteredCheckoutRecords(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS),
                        null
                ));

        DefaultTableModel tableModel = TableUtil.getDefaultTableModel(columns, rows);
        return tableModel;
    }
    public void refreshTable() {

        DefaultTableModel tableModel = getDefaultTableModel();

        table.setModel(tableModel);
        table.revalidate();
        table.repaint();
    }

    private void createTable() {
        lowerPanel = new JPanel();

        // Create a table model with predefined data

        DefaultTableModel tableModel = getDefaultTableModel();

        // Create a JTable with the table model
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(710,340));
        // Add the JTable to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        lowerPanel.add(scrollPane);


    }
    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            Dashboard.INSTANCE.init();
        });
    }

    private void createInfoPanel() {

        JPanel boxPanel1 = createNumberBox("Library Members", systemController.getTotalLibraryMemberss());
        JPanel boxPanel2 = createNumberBox("Books", systemController.getTotalBooks());
        JPanel boxPanel3 = createNumberBox("Checked Out Books", systemController.getTotalCheckedOut());

        boxPanel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(SystemController.currentAuth == Auth.ADMIN || SystemController.currentAuth == Auth.BOTH){
                    LibrarySystem.hideAllWindows();
                    Dashboard.INSTANCE.setVisible(false);
                    LibraryMemberListWindow.INSTANCE.init();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        boxPanel2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(SystemController.currentAuth == Auth.ADMIN || SystemController.currentAuth == Auth.BOTH) {
                    Dashboard.INSTANCE.setVisible(false);
                    AddBookCopy.INSTANCE.init();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        boxPanel3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(SystemController.currentAuth == Auth.LIBRARIAN || SystemController.currentAuth == Auth.BOTH) {
                    Dashboard.INSTANCE.setVisible(false);

                    CheckOutWindow.INSTANCE.init();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        // Create a main panel with FlowLayout
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        infoPanel.add(boxPanel1);
        infoPanel.add(boxPanel2);
        infoPanel.add(boxPanel3);
    }


    private JPanel createNumberBox(String title, String number) {
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), Font.BOLD, 13));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberLabel.setFont(new Font(numberLabel.getFont().getFontName(), Font.PLAIN, 18));

        boxPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        boxPanel.add(titleLabel);
        boxPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        boxPanel.add(numberLabel);

        // Optional: Add border for visual separation
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boxPanel.setPreferredSize(new Dimension(180,65));
        boxPanel.setBackground(new Color(224, 224, 224));
        return boxPanel;
    }

    private Dashboard(){};



}
