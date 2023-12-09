package librarysystem;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import validation.RuleException;
import validation.RuleSet;
import validation.RuleSetFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.*;

public class CheckOutWindow extends JFrame implements LibWindow {
    public static final CheckOutWindow INSTANCE = new CheckOutWindow();
	private final SystemController ci;

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel container;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;

	private JTextField memberId;
	private JTextField bookISBN;
	private JLabel label;
	private JButton loginButton;
	private JButton logoutButton;
	public JTable table;
	private JButton checkoutButton;


	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
    private CheckOutWindow() {
		ci = new SystemController();
	}
    
    public void init() {
			if(this.isInitialized){
				CheckOutWindow.INSTANCE.setVisible(true);
				refreshTable();
				return;
			}
			isInitialized(true);
			if(mainPanel != null){
				return;
			}
    		mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    		defineUpperHalf();
    		defineMiddleHalf();
    		defineLowerHalf();

    					
    		mainPanel.add(upperHalf, BorderLayout.NORTH);
    		mainPanel.add(middleHalf, BorderLayout.CENTER);
    		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
    		getContentPane().add(mainPanel);
    		isInitialized(true);
//    		pack();
			setSize(800, 600);
			setMaximumSize(new Dimension(800, 600));
			setMinimumSize(new Dimension(800, 600));
			Util.centerFrameOnDesktop(CheckOutWindow.INSTANCE);
			setVisible(true);

    	
    }

    private void defineUpperHalf() {
    		
    		upperHalf = new JPanel();
    		upperHalf.setLayout(new BorderLayout());
    		defineTopPanel();
    		defineMiddlePanel();
    		defineLowerPanel();
    		upperHalf.add(topPanel, BorderLayout.NORTH);
    		upperHalf.add(middlePanel, BorderLayout.CENTER);
    		upperHalf.add(lowerPanel, BorderLayout.SOUTH);
    		
    	}
    	private void defineMiddleHalf() {
    		middleHalf = new JPanel();
    		middleHalf.setLayout(new BorderLayout());
    		JSeparator s = new JSeparator();
    		s.setOrientation(SwingConstants.HORIZONTAL);
    		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
    		middleHalf.add(s, BorderLayout.SOUTH);
    		
    	}
    	private void defineLowerHalf() {

    		lowerHalf = new JPanel();
    		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));


			JButton backButton = new JButton("< Back");
			addBackButtonListener(backButton);
			lowerHalf.add(backButton);
    		
    	}
    	private void defineTopPanel() {
    		topPanel = new JPanel();
    		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

			JLabel title = new JLabel("Library Management System - Checkout");
			title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 20));
    		topPanel.add(title);


    	}
    	
    	
    	
    	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
    		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    		defineLeftTextPanel();
    		defineRightTextPanel();
    		middlePanel.add(leftTextPanel);
    		middlePanel.add(rightTextPanel);


			checkoutButton = new JButton("Checkout");
			addCheckoutButtonListener(checkoutButton);
			middlePanel.add(checkoutButton);

		}
    	private void defineLowerPanel() {
    		lowerPanel = new JPanel();
			// Create a JTable with the table model
			table = new JTable(getDefaultTableModel());
			// Add the JTable to a scroll pane
			table.setPreferredScrollableViewportSize(new Dimension(770,400));
			JScrollPane scrollPane = new JScrollPane(table);

			refreshTable();
			lowerPanel.add(scrollPane);

    	}



	private void defineLeftTextPanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

		memberId = new JTextField(10);
		label = new JLabel("Member Id");
		label.setFont(Util.makeSmallFont(label.getFont()));
		topText.add(memberId);
		bottomText.add(label);

		leftTextPanel = new JPanel();
		leftTextPanel.setLayout(new BorderLayout());
		leftTextPanel.add(topText,BorderLayout.NORTH);
		leftTextPanel.add(bottomText,BorderLayout.CENTER);
	}
	private void defineRightTextPanel() {

		JPanel topText = new JPanel();
		JPanel bottomText = new JPanel();
		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

		bookISBN = new JTextField(10);
		label = new JLabel("Book ISBN");
		label.setFont(Util.makeSmallFont(label.getFont()));
		topText.add(bookISBN);
		bottomText.add(label);



		rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new BorderLayout());
		rightTextPanel.add(topText,BorderLayout.NORTH);
		rightTextPanel.add(bottomText,BorderLayout.CENTER);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			CheckOutWindow.INSTANCE.setVisible(false);
			Dashboard.INSTANCE.init();
		});
	}

	private void addCheckoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {

			RuleSet rules = RuleSetFactory.getRuleSet(this);
			try {
				rules.applyRules(this);
			} catch (RuleException e) {
				System.out.println(e.getMessage());
				return;
			}
			try {
				ci.checkoutBook(getMemberId(), getBookISBN());
			}
			catch (RuleException ex){
				JOptionPane.showMessageDialog(this,ex.getMessage());
			}
			refreshTable();
		});
	}

	public void refreshTable() {

		DefaultTableModel tableModel = getDefaultTableModel();

		table.setModel(tableModel);
		table.revalidate();
		table.repaint();
	}

	private DefaultTableModel getDefaultTableModel() {
		List<String> columns = TableUtil.getColumnsCheckout();

		Object[][] rows = TableUtil.getRowsCheckout(ci.allCheckoutRecords());

		DefaultTableModel tableModel = TableUtil.getDefaultTableModel(columns, rows);
		return tableModel;
	}

	public String getMemberId(){
		return memberId.getText();
	}
	public String getBookISBN(){
		return bookISBN.getText();
	}
}
