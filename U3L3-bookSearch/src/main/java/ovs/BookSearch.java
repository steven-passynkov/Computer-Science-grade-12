/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ovs;

import java.awt.Color;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * File Name: BookSearch.java
 * Person Programmer: Steven Passynkov 
 * Date: 14 Sep 2024 
 * Description: This program will find the tile of a book according to the
 * library reference number. The program will use a Binary Search and a Linear
 * Search.
 */
public class BookSearch extends javax.swing.JFrame {

    private List<Book> books;

    public BookSearch() {
        // init components
        initComponents();
        // set empty text label for linearSearch
        linearSearch.setText("");
        // set empty text label for binarySearch
        binarySearch.setText("");
        // set long description using html to wrap
        this.descLabel.setText("<html>"
                + "This program will find the tile of a book according to the library reference number. The program will use a Binary Search and a Linear Search." + "<html>");

        // load Books from file
        loadBooksFromJson("Books.json");
    }

    /**
     * Load books from JSON file from file name
     *
     * @param fileName File name
     */
    private void loadBooksFromJson(String fileName) {
        // create array list of books
        books = new ArrayList<>();
        // create buffered file reader
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // binarySearch that contains all json
            StringBuilder jsonString = new StringBuilder();
            // variable for line
            String line;

            // read line by line 
            while ((line = reader.readLine()) != null) {
                // add line to  jsonString
                jsonString.append(line);
            }

            // convert jsonString to JSON Array
            JSONArray jsonArray = new JSONArray(jsonString.toString());

            // iterate over jsonArray
            for (int i = 0; i < jsonArray.length(); i++) {
                //get JSONObject by index
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // get id prop
                int id = jsonObject.getInt("id");
                // get title prop
                String title = jsonObject.getString("title");
                // create a book with id and title and add to books list
                books.add(new Book(id, title));
            }
        } catch (IOException e) {
            // show error in case of exception
            JOptionPane.showMessageDialog(null, "Can't Load Books from file",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Liner search - just go over list
     *
     * @param books List of books
     * @param id id to look
     * @return null if not found or book
     */
    private Book linearSearch(List<Book> books, int id) {
        // iterate over books
        for (Book book : books) {
            // if id equal to boo id
            if (book.getId() == id) {
                // return book
                return book;
            }
        }
        // not found - null
        return null;
    }

    /**
     * Binary search
     *
     * @param books List of books
     * @param id id to look
     * @param low Low bound
     * @param high High bound
     * @return -1 if not found or index of book
     */
    public int binarySearch(List<Book> books, int id, int low, int high) {
        // if the range is invalid
        if (low > high) {
            return -1;
        }

        // middle index
        int mid = low + (high - low) / 2;

        // book at the middle index, get its Id
        int midId = books.get(mid).getId();

        // midId equal id 
        if (midId == id) {
            // found
            return mid;
        } else if (midId < id) {
            // search right half of list
            return binarySearch(books, id, mid + 1, high);
        } else {
            // search left half of list
            return binarySearch(books, id, low, mid - 1);
        }
    }

    /**
     * Execute search
     */
    private void search() {
        try {
            // get input and parse to id
            int inputInt = Integer.parseInt(input.getText().trim());

            // Do liner search
            Book linearResult = linearSearch(books, inputInt);
            // if found
            if (linearResult != null) {
                // display in black font
                linearSearch.setForeground(Color.BLACK);
                // display book's title
                linearSearch.setText(String.format("Linear Search: %s", linearResult.getTitle()));
            } else {
                // display in red font
                linearSearch.setForeground(Color.RED);
                // display message that not found
                linearSearch.setText("Linear Search: Book not found.");
            }

            // do binary search
            int binaryIndex = binarySearch(books, inputInt, 0, books.size() - 1);
            // if index > -1 - return book
            if (binaryIndex > -1) {
                // get book by index
                Book binaryResult = books.get(binaryIndex);
                // display in black font
                binarySearch.setForeground(Color.BLACK);
                // display book's title
                binarySearch.setText(String.format("Binary Search: %s", binaryResult.getTitle()));
            } else {
                // display in red font
                binarySearch.setForeground(Color.RED);
                // display message that not found
                binarySearch.setText("Binary Search: Book not found.");
            }
        } catch (NumberFormatException e) {
            // reset labels
            linearSearch.setText("");
            binarySearch.setText("");
            // show error
            JOptionPane.showMessageDialog(null, "Invalid Number",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        input = new javax.swing.JTextField();
        linearSearch = new javax.swing.JLabel();
        binarySearch = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        inputLabel = new javax.swing.JLabel();
        descLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });

        linearSearch.setText("Linear Search:");

        binarySearch.setText("Binary Search:");

        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 0, 0));
        titleLabel.setText("Children's classics");
        titleLabel.setToolTipText("");

        inputLabel.setText("Book Reference ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(titleLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(binarySearch)
                            .addComponent(descLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(linearSearch)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(inputLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(descLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputLabel))
                .addGap(18, 18, 18)
                .addComponent(searchBtn)
                .addGap(17, 17, 17)
                .addComponent(linearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(binarySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        // perform search
        search();
    }//GEN-LAST:event_inputActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // perform search
        search();
    }//GEN-LAST:event_searchBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookSearch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel binarySearch;
    private javax.swing.JLabel descLabel;
    private javax.swing.JTextField input;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JLabel linearSearch;
    private javax.swing.JButton searchBtn;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
