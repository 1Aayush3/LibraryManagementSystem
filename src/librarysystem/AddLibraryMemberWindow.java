package librarysystem;

import business.*;
import validation.RuleException;
import validation.RuleSet;
import validation.RuleSetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddLibraryMemberWindow extends JFrame implements LibWindow {

    private JFrame frame;

    public static AddLibraryMemberWindow INSTANCE= new AddLibraryMemberWindow();

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getMemberIdTextField() {
        return memberIdTextField;
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public JTextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }

    public JTextField getCityTextField() {
        return cityTextField;
    }

    public JTextField getStateTextField() {
        return stateTextField;
    }

    public JTextField getStreetTextField() {
        return streetTextField;
    }

    public JTextField getZipCodeTextField() {
        return zipCodeTextField;
    }

    private JTextField memberIdTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField phoneNumberTextField;
    private JTextField cityTextField;
    private JTextField stateTextField;
    private JTextField streetTextField;
    private JTextField zipCodeTextField;

    private boolean isInitialized = false;

    private  AddLibraryMemberWindow(){}

    @Override
    public void init() {

        isInitialized(true);

        frame = new JFrame("S.A.D Library System - Add Member");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(500, 100, 433, 550);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        // Member ID
        memberIdTextField = new JTextField();
        memberIdTextField.setBounds(160, 26, 163, 26);
        memberIdTextField.setEditable(false);
        memberIdTextField.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        frame.getContentPane().add(memberIdTextField);
        memberIdTextField.setColumns(10);
        memberIdTextField.setText(SystemController.generateNewMemberId());

        JTextField memberIdLabel = new JTextField("Member ID #");
        memberIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        memberIdLabel.setBounds(55, 26, 93, 26);
        memberIdLabel.setEditable(false);
        memberIdLabel.setBorder(null);
        frame.getContentPane().add(memberIdLabel);

        // Personal Details
        JLabel personalDetailsLabel = new JLabel("Personal Details");
        personalDetailsLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        personalDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        personalDetailsLabel.setBounds(152, 60, 127, 33);
        frame.getContentPane().add(personalDetailsLabel);

        // First Name
        firstNameTextField = new JTextField();
        firstNameTextField.setBounds(160, 102, 163, 26);
        frame.getContentPane().add(firstNameTextField);
        firstNameTextField.setColumns(10);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstNameLabel.setBounds(55, 102, 93, 16);
        frame.getContentPane().add(firstNameLabel);

        // Last Name
        lastNameTextField = new JTextField();
        lastNameTextField.setBounds(160, 140, 163, 26);
        frame.getContentPane().add(lastNameTextField);
        lastNameTextField.setColumns(10);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lastNameLabel.setBounds(65, 140, 83, 16);
        frame.getContentPane().add(lastNameLabel);

        // Phone Number
        phoneNumberTextField = new JTextField();
        phoneNumberTextField.setColumns(10);
        phoneNumberTextField.setBounds(160, 178, 163, 26);
        frame.getContentPane().add(phoneNumberTextField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(55, 178, 100, 16);
        frame.getContentPane().add(phoneNumberLabel);

        // Address
        JLabel addressLabel = new JLabel("ADDRESS");
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addressLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        addressLabel.setBounds(152, 222, 110, 33);
        frame.getContentPane().add(addressLabel);

        // City
        cityTextField = new JTextField();
        cityTextField.setColumns(10);
        cityTextField.setBounds(160, 263, 163, 26);
        frame.getContentPane().add(cityTextField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cityLabel.setBounds(65, 263, 93, 16);
        frame.getContentPane().add(cityLabel);

        // State
        stateTextField = new JTextField();
        stateTextField.setColumns(10);
        stateTextField.setBounds(160, 301, 163, 26);
        frame.getContentPane().add(stateTextField);

        JLabel stateLabel = new JLabel("State:");
        stateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stateLabel.setBounds(65, 301, 93, 16);
        frame.getContentPane().add(stateLabel);

        // Street
        streetTextField = new JTextField();
        streetTextField.setColumns(10);
        streetTextField.setBounds(160, 339, 163, 26);
        frame.getContentPane().add(streetTextField);

        JLabel streetLabel = new JLabel("Street:");
        streetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        streetLabel.setBounds(65, 339, 93, 16);
        frame.getContentPane().add(streetLabel);

        // Zip Code
        zipCodeTextField = new JTextField();
        zipCodeTextField.setColumns(10);
        zipCodeTextField.setBounds(160, 377, 163, 26);
        frame.getContentPane().add(zipCodeTextField);

        JLabel zipCodeLabel = new JLabel("Zip Code:");
        zipCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        zipCodeLabel.setBounds(65, 377, 93, 16);
        frame.getContentPane().add(zipCodeLabel);

        // Add Member Button
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.setBounds(141, 430, 138, 38);
        addMemberButtonListener(addMemberButton);
        frame.getContentPane().add(addMemberButton);
        frame.setResizable(false);
    }

    private void addMemberButtonListener(JButton btn) {
        btn.addActionListener(evt -> {
            RuleSet rules = RuleSetFactory.getRuleSet(this);
            try{
                rules.applyRules(this);
            }
            catch (RuleException e) {
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
            saveMember();
            LibrarySystem.hideAllWindows();
            LibraryMemberListWindow.INSTANCE.init();
        });
    }

    public void saveMember() {
        new SystemController().addLibraryMember(
                new LibraryMember(
                        memberIdTextField.getText(),
                        firstNameTextField.getText().trim(),
                        lastNameTextField.getText().trim(),
                        phoneNumberTextField.getText().trim(),
                        new Address(
                                streetTextField.getText().trim(),
                                cityTextField.getText().trim(),
                                stateTextField.getText().trim(),
                                zipCodeTextField.getText().trim()
                        )
                )
        );
        this.setVisible(false);
    }


    private void clear() {

        final JTextField[] textFields = {
                firstNameTextField,
                lastNameTextField,
                streetTextField,
                cityTextField,
                stateTextField,
                zipCodeTextField,
                phoneNumberTextField,};

        for (final var textField : textFields) {
            textField.setText(
                    "");
        }

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {

        isInitialized = val;

    }

    @Override
    public void setVisible(boolean b) {

    }
}
