package librarysystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static librarysystem.CheckOutWindow.getDefaultTableModel;

public class Dashboard extends JFrame {

    public static final Dashboard INSTANCE = new Dashboard();

    private JPanel infoPanel;
    private JPanel lowerPanel;
    public JTable table;

    void init(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660,500);
        this.setVisible(true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Library Management System"));

        createInfoPanel();
        createTable();

        JPanel tableLabel = new JPanel(new BorderLayout());
        tableLabel.add(new JLabel("Checked out books with due date today!"));

        mainPanel.add(topPanel);
        mainPanel.add(infoPanel);
        mainPanel.add(tableLabel);
        mainPanel.add(lowerPanel);

        // Set the main panel as the content pane
        add(mainPanel);
        setVisible(true);
    }

    private void createTable() {
        lowerPanel = new JPanel();

        // Create a table model with predefined data

        DefaultTableModel tableModel = getDefaultTableModel();

        // Create a JTable with the table model
        table = new JTable(tableModel);

        // Add the JTable to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        lowerPanel.add(scrollPane);
    }

    private void createInfoPanel() {

        JPanel boxPanel1 = createNumberBox("Library Members", "123");
        JPanel boxPanel2 = createNumberBox("Books", "456");
        JPanel boxPanel3 = createNumberBox("Checked Out Books", "789");

        boxPanel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        boxPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        boxPanel.add(titleLabel);
        boxPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
        boxPanel.add(numberLabel);

        // Optional: Add border for visual separation
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boxPanel.setPreferredSize(new Dimension(180,60));
        boxPanel.setBackground(new Color(173, 216, 230));
        return boxPanel;
    }

    private Dashboard(){};



}
