/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ovs;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.Border;

/**
 * File Name:	IntegerSums 
 * Programmer:	Steven Passynkov 
 * Date:	09 Apr 2024
 * Description:	The program manages integers: import, export, add, delete,
 * filter and calculate sum
 */
public class IntegerSums extends javax.swing.JFrame {

    /**
     * Creates new form IntegerSums
     */
    public IntegerSums() {
        // init generated componenets
        initComponents();
        // initialize Action column with DeleteButtonRenderer to render button
        table.getColumnModel().getColumn(2).setCellRenderer(new DeleteButtonRenderer());
        // initialize Action column with DeleteButtonEditor to do delete operation
        table.getColumnModel().getColumn(2).setCellEditor(new DeleteButtonEditor());
        // initialize Action column with NumericCellRenderer to render TextField and handle not integers
        table.getColumnModel().getColumn(0).setCellRenderer(new NumericCellRenderer());
        // initialize Action column with NumericCellEditor to render TextField and handle move out 
        table.getColumnModel().getColumn(0).setCellEditor(new NumericCellEditor());

        // filter number to have RowFilter on table
        filterNumbers();
    }

    /**
     * Cell renderer for 1st column to show in red not integers
     */
    private class NumericCellRenderer extends DefaultTableCellRenderer {

        // default border
        private Border defaultBorder;
        // red border
        private Border redBorder;

        /**
         * Create NumericCellRenderer
         */
        public NumericCellRenderer() {
            // create empty border
            defaultBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
            // create red border
            redBorder = BorderFactory.createLineBorder(Color.RED, 1);
        }

        /**
         * Check if value should have red border
         *
         * @param value value in cell
         * @return If value - empty true, if integer- true, else false
         */
        private boolean isNumericOrEmpty(Object value) {
            // empty - true
            if (value == null || value.equals("")) {
                return true;
            }
            try {
                // value to String and parse as Integer
                Integer.parseInt(value.toString());
                // parse Integer - return true
                return true;
            } catch (NumberFormatException e) {
                // exception during conversion - means not Integer
                return false;
            }
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            // Get parent default
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check if the value is not a integer or not empty
            if (!isNumericOrEmpty(value)) {
                // set red border
                ((JComponent) rendererComponent).setBorder(redBorder);
            } else {
                // otherwise, set the default border
                ((JComponent) rendererComponent).setBorder(defaultBorder);
            }

            // return modified rendererComponent
            return rendererComponent;
        }
    }

    /**
     * NumericCellEditor for 1st cell to be able to add a value when course
     * moved out from cell. Sorry Focus Listener DOES NOT work becuase for Focus
     * Listener to work, the focus need to be moved to another element with
     * focus support like click on button and it will not obvious for user
     */
    public class NumericCellEditor extends AbstractCellEditor implements TableCellEditor {

        // text field
        private JTextField textField;

        public NumericCellEditor() {
            // create text field
            textField = new JTextField();
            // set border to null during insert
            textField.setBorder(null);

            // implement mouseExited event to add a value when mouse moved out from cell
            textField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    String value = textField.getText();
                    try {
                        // try to get Integer value from cell
                        Integer intValue = Integer.valueOf(value);
                        // get model
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        // get selected row
                        int row = table.getSelectedRow();

                        // get flag if number even
                        boolean isEvenValue = isEven(intValue);
                        // set the flag to model
                        model.setValueAt(isEvenValue, row, 1);

                        // stop editing
                        table.getCellEditor().stopCellEditing();
                        // remove row selection
                        table.getSelectionModel().clearSelection();

                        // filter numbers
                        filterNumbers();
                        // caclulate sum after adding/chaning number
                        calculateSum();

                    } catch (Exception ex) {
                        // keep wrong value in table - so urse can fix it after
                        // stop editing
                        table.getCellEditor().stopCellEditing();
                        // remove row selection
                        table.getSelectionModel().clearSelection();

                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // set value from cell to text field
            textField.setText(value != null ? value.toString() : "");
            // retun text field
            return textField;
        }

        @Override
        public Object getCellEditorValue() {
            // get text of the textField
            return textField.getText();
        }
    }

    /**
     * Renderer class for Delete button
     */
    private class DeleteButtonRenderer extends JComponent implements TableCellRenderer {

        // Delete button
        private final JButton button;

        /**
         * Constructor
         */
        public DeleteButtonRenderer() {
            // Set layout BoxLayout to be able to customize width of the button
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            // create the button
            button = new JButton("Delete");
            // add spacer before (margin left)
            add(Box.createHorizontalGlue());
            // add button
            add(button);
            // add space after (margin right)
            add(Box.createHorizontalGlue());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // just return the render
            return this;
        }
    }

    /**
     * Editor class for Delete button
     */
    private class DeleteButtonEditor extends AbstractCellEditor implements TableCellEditor {
// Delete button

        private JButton button;
        // JTable
        private JTable table;

        public DeleteButtonEditor() {
            // create the button
            button = new JButton("Delete");
            // add button clikc listener
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // get index of ow that is edited (delete button clicked)
                    int modelRowIndex = table.convertRowIndexToModel(table.getEditingRow());
                    // get JTable model
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // remove row from model by index
                    model.removeRow(modelRowIndex);
                    // notify that editing finished
                    fireEditingStopped();
                    // recalculate sum
                    calculateSum();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // link JTable to editor
            this.table = table;
            // return Delete button
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            // get text of the button
            return button.getText();
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

        jLabel1 = new javax.swing.JLabel();
        importBtn = new javax.swing.JButton();
        exportBtn = new javax.swing.JButton();
        addRowBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        evenCheckbox = new javax.swing.JCheckBox();
        oddCheckbox = new javax.swing.JCheckBox();
        jScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        sumLabel = new javax.swing.JLabel();
        showLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("Integer Sums");

        importBtn.setText("Import");
        importBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importBtnActionPerformed(evt);
            }
        });

        exportBtn.setText("Export");
        exportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBtnActionPerformed(evt);
            }
        });

        addRowBtn.setText("Add Row");
        addRowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowBtnActionPerformed(evt);
            }
        });

        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        evenCheckbox.setSelected(true);
        evenCheckbox.setText("Even");
        evenCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evenCheckboxActionPerformed(evt);
            }
        });

        oddCheckbox.setSelected(true);
        oddCheckbox.setText("Odd");
        oddCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oddCheckboxActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Number", "Is Even", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(20);
        table.getTableHeader().setReorderingAllowed(false);
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        sumLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        sumLabel.setText("Sum: ");

        showLabel.setText("Show");

        jButton1.setText("Help?");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(addRowBtn)
                .addGap(280, 280, 280)
                .addComponent(showLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(evenCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(oddCheckbox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(importBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportBtn)
                        .addComponent(importBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(evenCheckbox)
                    .addComponent(showLabel)
                    .addComponent(oddCheckbox)
                    .addComponent(addRowBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitBtn)
                    .addComponent(sumLabel))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Calculate sum of filtered rows
     */
    private void calculateSum() {
        // initil sum
        int sum = 0;
        // get filtered rwos (even, off or all)
        List<Integer> rows = getFilteredRows();

        // for each row
        for (int row : rows) {
            // get row index
            int modelRow = table.convertRowIndexToModel(row);
            // get number form the row
            Object value = table.getModel().getValueAt(modelRow, 0);
            // if now numver -> continue
            if (value == null) {
                continue;
            }
            // try to convert value to Integer and add it to sum
            try {
                sum += Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                // skip not Integeres
            }

        }
        // update sumLabel with sum
        sumLabel.setText("Sum: " + sum);
    }

    /**
     * Get rows based on even , odd filters
     *
     * @return List of rows
     */
    private List<Integer> getFilteredRows() {
        // create ArrayList
        List<Integer> filteredRows = new ArrayList<>();
        // get associated TableRowSorter
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();

        //if no - sorry but it should be
        if (sorter != null) {
            // go over all rows
            for (int i = 0; i < table.getRowCount(); i++) {
                // add row to list of filteredRows
                filteredRows.add(i);
            }
        }
        // return filteredRows
        return filteredRows;
    }

    /**
     * Filter integers based on evenCheckbox and oddCheckbox
     */
    private void filterNumbers() {
        // create TableRowSorter 
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        // set it to the table
        table.setRowSorter(sorter);

        RowFilter<Object, Object> rowFilter = new RowFilter<Object, Object>() {

            @Override
            public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
                // implment include method

                // get value of evenCheckbox
                boolean showEven = evenCheckbox.isSelected();
                // get value of oddCheckbox
                boolean showOdd = oddCheckbox.isSelected();
                // get isEven value from entry as object
                Object valueObj = entry.getValue(1);
                // if it is set - do filter
                if (valueObj instanceof Boolean) {
                    // convert to boolean
                    boolean isEven = (boolean) valueObj;
                    // if showeven checkbox and valud is even - show row
                    // if showOdd checkbox and valud is odd - show row
                    if ((showEven && isEven) || (showOdd && !isEven)) {
                        return true;
                    } else {
                        // otherwise false
                        return false;
                    }
                }
                // how new record
                return true;
            }
        };

        // filter table using rowFilter
        sorter.setRowFilter(rowFilter);
    }

    /**
     * Return true if object is even
     *
     * @param value Object that has
     * @return true if object is even
     */
    private boolean isEven(Object value) {
        // convert to number
        int number = ((Number) value).intValue();
        // check if even
        boolean isEven = number % 2 == 0;
        // return result
        return isEven;
    }

    /**
     * Implement action when Import btn is clicked
     *
     * @param evt
     */
    private void importBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importBtnActionPerformed
        // create file chooser
        JFileChooser fileChooser = new JFileChooser();
        // show it
        int result = fileChooser.showOpenDialog(null);

        // if yes
        if (result == JFileChooser.APPROVE_OPTION) {
            // get selected file
            File selectedFile = fileChooser.getSelectedFile();

            // create reader
            BufferedReader reader = null;
            try {
                // red file
                reader = new BufferedReader(new FileReader(selectedFile));
                // get table model
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                // line will be used to read each line in file
                String line;
                // go line by line
                while ((line = reader.readLine()) != null) {
                    // read value
                    Integer value = Integer.valueOf(line);
                    // create a new row 
                    Object[] newRow = {null, null, null};
                    // add the new row to the table model
                    model.addRow(newRow);
                    // get row index
                    Integer index = model.getRowCount() - 1;
                    // set isEven flag for value to cell
                    model.setValueAt(isEven(value), index, 1);
                    // set value to cell
                    model.setValueAt(value, index, 0);
                }

                // filter numbers
                filterNumbers();
                // Calculate the sum
                calculateSum();
            } catch (IOException e) {
                //log exception
                e.printStackTrace();
                // show error
                JOptionPane.showMessageDialog(null, "Error importing table data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            } finally {
                // in any case close reader
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }//GEN-LAST:event_importBtnActionPerformed

    /**
     * Implement action when Export btn is clicked
     *
     * @param evt
     */
    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBtnActionPerformed
        // create file chooser
        JFileChooser fileChooser = new JFileChooser();
        // show it
        int result = fileChooser.showSaveDialog(null);

        // user selected file
        if (result == JFileChooser.APPROVE_OPTION) {
            // get file
            File selectedFile = fileChooser.getSelectedFile();
            // get model
            TableModel model = table.getModel();

            BufferedWriter writer = null;
            try {
                // open file writer
                writer = new BufferedWriter(new FileWriter(selectedFile));
                // get over rows
                for (int row = 0; row < model.getRowCount(); row++) {
                    // get number from 1st cell from row
                    Object value = model.getValueAt(row, 0);
                    // if it exist
                    if (value != null) {
                        // write it as string
                        writer.write(value.toString());
                    }
                    // add new line 
                    writer.write("\n");
                }

            } catch (IOException e) {
                // log exception
                e.printStackTrace();
                // in case of error - show exception
                JOptionPane.showMessageDialog(null, "Error exporting table data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                // in any case close write
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }//GEN-LAST:event_exportBtnActionPerformed

    /**
     * Implement action when Enter btn is clicked
     *
     * @param evt
     */
    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // get model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // get selected row
            int row = table.getSelectedRow();
            // get value from row
            Object value = model.getValueAt(row, 0);

            // if value there
            if (value != null && value instanceof Number) {
                // get flag if number even
                boolean isEvenValue = isEven(value);
                // set the flag to model
                model.setValueAt(isEvenValue, row, 1);
            }

            // filter numbers
            filterNumbers();
            // caclulate sum after adding/chaning number
            calculateSum();
        }
    }//GEN-LAST:event_tableKeyReleased

    /**
     * Implement action when Add Row btn is clicked
     *
     * @param evt
     */
    private void addRowBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowBtnActionPerformed
        // get model
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        //create row
        Object[] newRow = {null, null, null};
        // add row to model
        model.addRow(newRow);
        // filter numbers
        filterNumbers();
        // calculate sum
        calculateSum();
    }//GEN-LAST:event_addRowBtnActionPerformed

    /**
     * Implement action when Even btn is clicked
     *
     * @param evt
     */
    private void evenCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evenCheckboxActionPerformed
        // filter numbers
        filterNumbers();
        // calculate sum
        calculateSum();
    }//GEN-LAST:event_evenCheckboxActionPerformed

    /**
     * Implement action when Odd btn is clicked
     *
     * @param evt
     */
    private void oddCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oddCheckboxActionPerformed
        // filter numbers
        filterNumbers();
        // calculate sum
        calculateSum();
    }//GEN-LAST:event_oddCheckboxActionPerformed

    /**
     * Implement action when exit btn is clicked
     *
     * @param evt
     */
    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // get model
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // set falg to check if there are rows
        boolean hasData = false;

        // go over all rows
        for (int row = 0; row < model.getRowCount(); row++) {
            // check if there is a number in 1st cell
            Object value = model.getValueAt(row, 0);
            // if it is there
            if (value != null) {
                // mean has data
                hasData = true;
                // break
                break;
            }
        }

        // if has data - show warning before exit
        if (hasData) {
            int choice = JOptionPane.showConfirmDialog(null, "The integer list is not empty. Do you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
            // user said - exit
            if (choice == JOptionPane.YES_OPTION) {
                // exit
                System.exit(0);
            }
        } else {
            // if no data - just exit
            System.exit(0);
        }
    }//GEN-LAST:event_exitBtnActionPerformed

    /**
     * Implement action when help btn is clicked
     *
     * @param evt
     */
    private void helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpActionPerformed
        // TODO add your handling code here:
        // show help
        JOptionPane.showMessageDialog(null, "<html>Integer Sum Program allows:"
                + "<br>1. Import and Export list of integers from/to file"
                + "<br>2. Edit table with integers. Click on number, change it "
                + "<br>3. You can add a new integer by Add button, input value. If value if not integer, a red border is shown."
                + "<br>4. Filtering even or odd number</html>");
    }//GEN-LAST:event_helpActionPerformed

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
            java.util.logging.Logger.getLogger(IntegerSums.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IntegerSums.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IntegerSums.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IntegerSums.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IntegerSums().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRowBtn;
    private javax.swing.JCheckBox evenCheckbox;
    private javax.swing.JButton exitBtn;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton importBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JCheckBox oddCheckbox;
    private javax.swing.JLabel showLabel;
    private javax.swing.JLabel sumLabel;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
