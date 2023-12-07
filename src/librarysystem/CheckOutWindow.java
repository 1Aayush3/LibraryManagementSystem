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
import java.util.HashMap;
import java.util.List;
import javax.swing.table.*;

public class CheckOutWindow extends JFrame implements LibWindow {
    public static final CheckOutWindow INSTANCE = new CheckOutWindow();

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
	private JTable table;


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
    private CheckOutWindow() {}
    
    public void init() {     		
    		mainPanel = new JPanel();
    		defineUpperHalf();
    		defineMiddleHalf();
    		defineLowerHalf();
    		BorderLayout bl = new BorderLayout();
    		bl.setVgap(30);
    		mainPanel.setLayout(bl);
    					
    		mainPanel.add(upperHalf, BorderLayout.NORTH);
    		mainPanel.add(middleHalf, BorderLayout.CENTER);
    		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
    		getContentPane().add(mainPanel);
    		isInitialized(true);
    		pack();
    		setSize(660, 500);

    	
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


			JButton backButton = new JButton("<= Back to Main");
			addBackButtonListener(backButton);
			lowerHalf.add(backButton);
    		
    	}
    	private void defineTopPanel() {
    		topPanel = new JPanel();
    		JPanel intPanel = new JPanel(new BorderLayout());
    		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
    		JLabel checkoutLabel = new JLabel("Checkout");
    		Util.adjustLabelFont(checkoutLabel, Color.BLUE.darker(), true);
    		intPanel.add(checkoutLabel, BorderLayout.CENTER);
    		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		topPanel.add(intPanel);


    	}
    	
    	
    	
    	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
    		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		defineLeftTextPanel();
    		defineRightTextPanel();
    		middlePanel.add(leftTextPanel);
    		middlePanel.add(rightTextPanel);


			JButton checkoutButton = new JButton("Checkout");
			addCheckoutButtonListener(checkoutButton);
			middlePanel.add(checkoutButton);

		}
    	private void defineLowerPanel() {
    		lowerPanel = new JPanel();

			// Create a table model with predefined data

			DefaultTableModel tableModel = getDefaultTableModel();

			// Create a JTable with the table model
			table = new JTable(tableModel);

			// Add the JTable to a scroll pane
			JScrollPane scrollPane = new JScrollPane(table);

			lowerPanel.add(scrollPane);

    	}

	private static DefaultTableModel getDefaultTableModel() {
		ControllerInterface ci = new SystemController();
		List<CheckoutRecord> checkoutRecords = ci.allCheckoutRecords();

		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("FirstName");
		tableModel.addColumn("LastName");
		tableModel.addColumn("Records");

		// Populate the table model
		for (CheckoutRecord object : checkoutRecords) {
			tableModel.addRow(new Object[] {
					object.getMember().getFirstName(),
					object.getMember().getLastName(),
					object.getCheckoutRecordId(),
//						object.getCheckoutRecordEntryList().size()
			});
		}
		return tableModel;
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

		public String getId(){
			return memberId.getText();
		}
    	private void defineRightTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

			String[] options = {"Option 1", "Option 2", "Option 3"};
			JComboBox<String> comboBox = new JComboBox<>(options);
			topText.add(comboBox);

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
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void addCheckoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {

			DataAccess da = new DataAccessFacade();
			HashMap<String, LibraryMember> mbrs = da.readMemberMap();
			HashMap<String, Book> bks = da.readBooksMap();

			List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();
			checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),bks.get(bks.keySet().toArray()[0]).getCopy(0)));
			checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),bks.get(bks.keySet().toArray()[0]).getCopy(1)));
			checkoutRecordEntries.add(new CheckoutRecordEntry(LocalDate.now(),LocalDate.now(),bks.get(bks.keySet().toArray()[0]).getCopy(0)));
			da.saveCheckoutRecord(new CheckoutRecord(""+Util.random.nextInt(100000),mbrs.get(mbrs.keySet().toArray()[0]),checkoutRecordEntries));

			DefaultTableModel tableModel = getDefaultTableModel();

			table.setModel(tableModel);
			table.revalidate();
			table.repaint();
		});
	}

	private void addLoginButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
				RuleSet rules = RuleSetFactory.getRuleSet(this);
				try {
					rules.applyRules(this);
				} catch (RuleException e) {
					System.out.println(e.toString());
				}
			});
    	}
}
