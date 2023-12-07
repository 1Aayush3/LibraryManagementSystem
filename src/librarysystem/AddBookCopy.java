package librarysystem;

import business.Book;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AddBookCopy extends JFrame {

    private JButton searchButton;
    private JTextField searchField;
    private JLabel searchResult;
    private JButton addBookCopy;
    AddBookCopy(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setVisible(true);

        searchField = new JTextField(20);
        searchResult = new JLabel();
        addBookCopy = new JButton("Add Book Copy");
        searchButton = new JButton("Search");
        searchButton.setVisible(false);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        JPanel panel = new JPanel();
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(searchResult);
        panel.add(addBookCopy);
        add(panel);
    }

    private void performSearch() {
        String searchTerm = searchField.getText();
        SystemController loginController = new SystemController();
        Book book = loginController.searchBookByISBN(searchTerm);

        if(book != null){
            searchResult.setText("Are you Searching for: " + book.getTitle());
            addBookCopy.setVisible(true);
        }else{
            searchResult.setText("Are you Searching for: ");
        }

    }



}
