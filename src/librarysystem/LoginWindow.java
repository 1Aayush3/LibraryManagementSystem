package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

import business.ControllerInterface;

import business.LoginException;
import business.SystemController;
import validation.RuleException;
import validation.RuleSet;
import validation.RuleSetFactory;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();
	
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
	
	private JTextField username;
	private JTextField password;
	private JLabel label;
	private JButton loginButton;
	private JButton logoutButton;
	private String pathToImage;


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
    private LoginWindow () {}
    
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
    		setSize(800, 600);
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
			setVisible(true);

			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
    	
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

    		
    	}
    	private void defineTopPanel() {
    		topPanel = new JPanel(new BorderLayout());
			topPanel.setBackground(Color.white);

			setPathToImage();
			ImageIcon image = new ImageIcon(pathToImage);
			topPanel.add(new JLabel(image), BorderLayout.NORTH);
		}
	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		String osName = System.getProperty("os.name");
		boolean isMac = osName.startsWith("Mac");
		if(isMac){
			pathToImage = currDirectory + "/src/librarysystem/miu_logo.jpg";
		}
		else {
			pathToImage = currDirectory + "\\src\\librarysystem\\miu_logo.jpg";
		}
	}
    	
    	
    	
    	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
    		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			middlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
			defineLeftTextPanel();
    		defineRightTextPanel();
    		middlePanel.add(leftTextPanel);
    		middlePanel.add(rightTextPanel);
    	}
    	private void defineLowerPanel() {
    		lowerPanel = new JPanel();
			lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    		loginButton = new JButton("Login");
    		addLoginButtonListener(loginButton);
    		lowerPanel.add(loginButton);
    	}

    	private void defineLeftTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));		
    		
    		username = new JTextField(10);
    		label = new JLabel("Username");
    		label.setFont(Util.makeSmallFont(label.getFont()));
    		topText.add(username);
    		bottomText.add(label);
    		
    		leftTextPanel = new JPanel();
    		leftTextPanel.setLayout(new BorderLayout());
    		leftTextPanel.add(topText,BorderLayout.NORTH);
    		leftTextPanel.add(bottomText,BorderLayout.CENTER);
    	}

		public String getId(){
			return username.getText();
		}

	public String getPassword(){
		return password.getText();
	}
    	private void defineRightTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));		
    		
    		password = new JPasswordField(10);
    		label = new JLabel("Password");
    		label.setFont(Util.makeSmallFont(label.getFont()));
    		topText.add(password);
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
    	
    	private void addLoginButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
				RuleSet rules = RuleSetFactory.getRuleSet(this);

				SystemController loginController = new SystemController();
                try {
					rules.applyRules(this);
                    loginController.login(getId(),getPassword());

					// If no exception is thrown, consider it a successful login
					JOptionPane.showMessageDialog(this, "Successful Login");
					this.setVisible(false);
					Dashboard.INSTANCE.init();
                } catch (RuleException e) {
					JOptionPane.showMessageDialog(this,e.getMessage());
                }
				username.setText("");
				password.setText("");
    		});
    	}
	
        
    
}
