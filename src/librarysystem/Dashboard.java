package librarysystem;

import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


public class Dashboard extends JFrame {

    public static final Dashboard INSTANCE = new Dashboard();

    private JPanel infoPanel;
    private JPanel lowerPanel;
    public JTable table;
    private SystemController systemController;
    private boolean isInitialized = false;
    private JLabel lblLibraryMembers;
    private JLabel lblTotalBooks;
    private JLabel lblCheckedOutBooks;

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
            refreshCount();
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
            LoginWindow.INSTANCE.setVisible(true);
        });
    }
    public void refreshCount(){
        if(lblLibraryMembers!=null && lblTotalBooks!=null && lblCheckedOutBooks!=null) {
            lblLibraryMembers.setText(systemController.getTotalLibraryMemberss());
            lblTotalBooks.setText(systemController.getTotalBooks());
            lblCheckedOutBooks.setText(systemController.getTotalCheckedOut());
        }
    }

    private void createInfoPanel() {
        HashMap<JPanel, JLabel> firstPanel = createNumberBox("Library Members", systemController.getTotalLibraryMemberss());
        JPanel boxPanel1 = (JPanel) firstPanel.keySet().toArray()[0];
        lblLibraryMembers = firstPanel.get(boxPanel1);

        HashMap<JPanel, JLabel> secondPanel = createNumberBox("Books", systemController.getTotalBooks());
        JPanel boxPanel2 = (JPanel) secondPanel.keySet().toArray()[0];
        lblTotalBooks = firstPanel.get(boxPanel2);


        HashMap<JPanel, JLabel> thirdPanel = createNumberBox("Checked Out Books", systemController.getTotalCheckedOut());
        JPanel boxPanel3 = (JPanel) thirdPanel.keySet().toArray()[0];
        lblCheckedOutBooks = firstPanel.get(boxPanel3);

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
                boxPanel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
                boxPanel1.setBackground(new Color(192, 192, 192));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boxPanel1.setBackground(new Color(224, 224, 224));
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
                    AddBookCopyWindow.INSTANCE.init();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boxPanel2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                boxPanel2.setBackground(new Color(192, 192, 192));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boxPanel2.setBackground(new Color(224, 224, 224));

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
                boxPanel3.setCursor(new Cursor(Cursor.HAND_CURSOR));
                boxPanel3.setBackground(new Color(192, 192, 192));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boxPanel3.setBackground(new Color(224, 224, 224));


            }
        });
        // Create a main panel with FlowLayout
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        infoPanel.add(boxPanel1);
        infoPanel.add(boxPanel2);
        infoPanel.add(boxPanel3);
    }


    private HashMap<JPanel,JLabel> createNumberBox(String title, String number) {
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

        HashMap<JPanel,JLabel> response = new HashMap<>();
        response.put(boxPanel,numberLabel);
        return response;
    }

    private Dashboard(){};



}
