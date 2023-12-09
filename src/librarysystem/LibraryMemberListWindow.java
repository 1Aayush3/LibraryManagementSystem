package librarysystem;

import business.SystemController;
import validation.RuleException;
import validation.RuleSet;
import validation.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryMemberListWindow extends JFrame implements LibWindow {
    public static final LibraryMemberListWindow INSTANCE = new LibraryMemberListWindow();
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

	private JLabel label;
	public JTable table;
	private JButton addMemberButton;


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
    private LibraryMemberListWindow() {
		ci = new SystemController();
	}
    
    public void init() {
			if(this.isInitialized){
				LibraryMemberListWindow.INSTANCE.setVisible(true);
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
			Util.centerFrameOnDesktop(LibraryMemberListWindow.INSTANCE);
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
    		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			JLabel title = new JLabel("Library Management System - Members");
			title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 20));
    		topPanel.add(title);


    	}
    	
    	
    	
    	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
    		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    		defineLeftTextPanel();
    		defineRightTextPanel();
    		middlePanel.add(leftTextPanel);
    		middlePanel.add(rightTextPanel);


			addMemberButton = new JButton("Add Member");
			addMemberButtonListener(addMemberButton);
			middlePanel.add(addMemberButton);

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



		rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new BorderLayout());
		rightTextPanel.add(topText,BorderLayout.NORTH);
		rightTextPanel.add(bottomText,BorderLayout.CENTER);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			Dashboard.INSTANCE.init();
		});
	}

	private void addMemberButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			AddLibraryMemberWindow.INSTANCE.init();
		});
	}

	public void refreshTable() {

		DefaultTableModel tableModel = getDefaultTableModel();

		table.setModel(tableModel);
		table.revalidate();
		table.repaint();
	}

	private DefaultTableModel getDefaultTableModel() {
		List<String> columns = TableUtil.getColumnsMembers();

		Object[][] rows = TableUtil.getRowsMembers(ci.allMembers());

		DefaultTableModel tableModel = TableUtil.getDefaultTableModel(columns, rows);
		return tableModel;
	}

}
