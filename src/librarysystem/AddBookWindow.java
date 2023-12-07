    package librarysystem;

    import business.Author;
    import business.Book;
    import dataaccess.DataAccessFacade;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.ArrayList;
    import java.util.List;

    public class AddBookWindow extends JFrame implements LibWindow {

        private JPanel mainPanel;

        private JPanel topPanel;
        private JPanel middlePanel;
        private JPanel lowerPanel;

        private JTextField isbnField;
        private JTextField titleField;
        private JTextField maxCheckoutLengthField;
        private JComboBox<String> authorsComboBox; // Change to JComboBox for selectable authors
        private JLabel isbnLabel;
        private JLabel titleLabel;
        private JLabel maxCheckoutLengthLabel;
        private JLabel authorsLabel;
        private JButton addBookButton;

        private DataAccessFacade dataAccessFacade;

        public AddBookWindow() {
            dataAccessFacade = new DataAccessFacade();
        }

        @Override
        public void init() {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            defineTopPanel();
            defineMiddlePanel();
            defineLowerPanel();
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(middlePanel, BorderLayout.CENTER);
            mainPanel.add(lowerPanel, BorderLayout.SOUTH);
            getContentPane().add(mainPanel);

            // Load authors into the ComboBox
            loadAuthorsComboBox();
        }

        @Override
        public boolean isInitialized() {
            return false;
        }

        @Override
        public void isInitialized(boolean val) {

        }

        private void loadAuthorsComboBox() {

            List<Author> allAuthors = dataAccessFacade.getAllAuthors();
            List<String> authorNames = new ArrayList<>();
            for (Author author : allAuthors) {
                authorNames.add(author.getFirstName());
            }
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(authorNames.toArray(new String[0]));
            authorsComboBox.setModel(model);
        }

        private void defineTopPanel() {
            topPanel = new JPanel();
            isbnLabel = new JLabel("ISBN:");
            isbnField = new JTextField(15);

            topPanel.add(isbnLabel);
            topPanel.add(isbnField);
        }

        private void defineMiddlePanel() {
            middlePanel = new JPanel();
            titleLabel = new JLabel("Title:");
            titleField = new JTextField(15);

            maxCheckoutLengthLabel = new JLabel("Max Checkout Length:");
            maxCheckoutLengthField = new JTextField(5);

            authorsLabel = new JLabel("Author:");
            authorsComboBox = new JComboBox<String>(); // ComboBox for selectable authors

            middlePanel.add(titleLabel);
            middlePanel.add(titleField);
            middlePanel.add(maxCheckoutLengthLabel);
            middlePanel.add(maxCheckoutLengthField);
            middlePanel.add(authorsLabel);
            middlePanel.add(authorsComboBox);
        }

        private void defineLowerPanel() {
            lowerPanel = new JPanel();
            addBookButton = new JButton("Add Book");
            addBookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addBook();
                }
            });

            lowerPanel.add(addBookButton);
        }

        private void addBook() {
            try {
                String isbn = isbnField.getText();
                String title = titleField.getText();
                int maxCheckoutLength = Integer.parseInt(maxCheckoutLengthField.getText());

                Author selectedAuthor = (Author) authorsComboBox.getSelectedItem();
                List<Author> authors = new ArrayList<>();
                authors.add(selectedAuthor);

                Book newBook = new Book(isbn, title, maxCheckoutLength, authors);
                dataAccessFacade.saveBook(newBook);

                System.out.println("New Book Created: " + newBook.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Max Checkout Length. Please enter a valid number.");
            }
        }
    }
