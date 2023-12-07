package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AddBookCopy extends JFrame {
    AddBookCopy(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setVisible(true);


        JPanel panel = new JPanel();

        // Sample data for the dropdown
        ArrayList<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");

        // Create a searchable dropdown
        JComboBox<String> comboBox = new JComboBox<>(items.toArray(new String[0]));
        JTextField searchField = new JTextField(15);

        // Add a key listener to the search field for filtering items
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchTerm = searchField.getText().toLowerCase();
                ArrayList<String> filteredItems = new ArrayList<>();
                for (String item : items) {
                    if (item.toLowerCase().contains(searchTerm)) {
                        filteredItems.add(item);
                    }
                }
                comboBox.setModel(new DefaultComboBoxModel<>(filteredItems.toArray(new String[0])));
            }
        });

        // Add action listener to the dropdown to handle item selection
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = comboBox.getSelectedItem().toString();
                JOptionPane.showMessageDialog(AddBookCopy.this, "Selected Item: " + selectedValue);
            }
        });

        panel.add(searchField);
        panel.add(comboBox);

        this.add(panel);
        this.setVisible(true);
    }


}
