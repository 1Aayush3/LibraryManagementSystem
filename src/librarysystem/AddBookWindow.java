package librarysystem;

import business.Author;
import business.Book;
import business.SystemController;
import dataaccess.DataAccessFacade;
import validation.RuleException;
import validation.RuleSet;
import validation.RuleSetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddBookWindow extends JFrame {

    public JTextField getIsbnTextField() {
        return isbnTextField;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JTextField getMaxCheckoutLengthTextField() {
        return maxCheckoutLengthTextField;
    }

    public JComboBox<String> getAuthorComboBox() {
        return authorComboBox;
    }

    private JTextField isbnTextField;
    private JTextField titleTextField;
    private JTextField maxCheckoutLengthTextField;
    private JComboBox<String> authorComboBox;

    public AddBookWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("Add New Book");
        setBounds(500, 100, 433, 550);

        JPanel mPanel = new JPanel(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnTextField = new JTextField();

        JLabel titleLabel = new JLabel("Title:");
        titleTextField = new JTextField();

        JLabel authorLabel = new JLabel("Author(s):");
        authorComboBox = new JComboBox<>();
        for (Author author :new SystemController().getAllAuthors()) {
            authorComboBox.addItem(author.getFullName()+" ("+author.getTelephone()+")");
        }

        JLabel maxCheckoutLengthLabel = new JLabel("Max Checkout Length:");
        maxCheckoutLengthTextField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitBook();
            }
        });

        mainPanel.add(isbnLabel);
        mainPanel.add(isbnTextField);

        mainPanel.add(titleLabel);
        mainPanel.add(titleTextField);

        mainPanel.add(maxCheckoutLengthLabel);
        mainPanel.add(maxCheckoutLengthTextField);

        mainPanel.add(authorLabel);
        mainPanel.add(authorComboBox);

        mPanel.add(mainPanel, BorderLayout.NORTH);

        JPanel jPanel = new JPanel();
        jPanel.add(submitButton);

        mPanel.add(jPanel,BorderLayout.SOUTH);
        add(mPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitBook() {
        String isbn = isbnTextField.getText().trim();
        String title = titleTextField.getText().trim();
        int maxCheckoutLength = Integer.parseInt(maxCheckoutLengthTextField.getText().trim());

        RuleSet rules = RuleSetFactory.getRuleSet(this);
        try{
            rules.applyRules(this);
            SystemController controller = new SystemController();

            String selectedTelephone = controller.getStringBetweenBrackets((String) authorComboBox.getSelectedItem());
            List<Author> selectedAuthor = new ArrayList<>();

            for (Author author : controller.getAllAuthors()) {
                if (author.getTelephone().equals(selectedTelephone)) {
                     selectedAuthor.add(author) ;
                }
            }

            controller.addBook(
                new Book(isbn,title,maxCheckoutLength,selectedAuthor)
            );
            JOptionPane.showMessageDialog(this, "Book Added:\nISBN: " + isbn + "\nTitle: " + title + "\nAuthor: "+authorComboBox.getSelectedItem() );
        }catch (RuleException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }

        isbnTextField.setText("");
        titleTextField.setText("");
        authorComboBox.setSelectedIndex(-1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddBookWindow());
    }
}
