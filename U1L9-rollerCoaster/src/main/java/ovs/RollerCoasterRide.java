package ovs;

import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.UIManager;

/**
 * File Name:	RollerCoasterRide.java
 * Person Programmer:	Steven Passynkov 
 * Date:	20 Feb 2024 
 * Description: Roller Coaster Ride program that checks if user is allowed to ride a rollercoaster.
 * Height.java is used to model height
 * HeightTest.java to test Height logic as unit test
 */
public class RollerCoasterRide extends javax.swing.JFrame {

    // Flag to tell if user can ride or not. Null if no decision or error
    private Boolean result = null;
    // Load english and french resource bundles. Use system locale as default
    private ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
    // create storeHeight that will keep current, min and max height
    private final Height storeHeight = new Height();

    /**
     * Creates new form NewJFrame
     */
    public RollerCoasterRide() {
        // init components
        initComponents();
        // set current locale to english
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        heightLabel = new javax.swing.JLabel();
        inputHight = new javax.swing.JTextField();
        backTroubleLabel = new javax.swing.JLabel();
        heartTroubleLabel = new javax.swing.JLabel();
        backTroubleCheckBox = new javax.swing.JCheckBox();
        heartTroubleCheckBox = new javax.swing.JCheckBox();
        calculateBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        unitsDropdown = new javax.swing.JComboBox<>();
        resetBtn = new javax.swing.JButton();
        langBtn = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Roller Coaster Ride");

        heightLabel.setText("Hight in");

        backTroubleLabel.setLabelFor(backTroubleCheckBox);
        backTroubleLabel.setText("Back Trouble");

        heartTroubleLabel.setLabelFor(heartTroubleCheckBox);
        heartTroubleLabel.setText("Heart Trouble");

        calculateBtn.setBackground(new java.awt.Color(51, 102, 255));
        calculateBtn.setForeground(new java.awt.Color(255, 255, 255));
        calculateBtn.setText("Calculate");
        calculateBtn.setToolTipText("");
        calculateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateBtnActionPerformed(evt);
            }
        });

        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        unitsDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cm", "in" }));
        unitsDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unitsDropdownActionPerformed(evt);
            }
        });

        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        langBtn.setText("EN");
        langBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(heightLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputHight, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(unitsDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(calculateBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(langBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exitBtn, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backTroubleCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backTroubleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(heartTroubleCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(heartTroubleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 91, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heightLabel)
                    .addComponent(inputHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unitsDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(langBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backTroubleLabel)
                        .addComponent(heartTroubleCheckBox)
                        .addComponent(heartTroubleLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backTroubleCheckBox)
                        .addGap(2, 2, 2)))
                .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calculateBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resetBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exitBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler for Calculate button
     *
     * @param evt
     */
    private void calculateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateBtnActionPerformed

        try {
            // get height from input
            String heightText = inputHight.getText();
            // convert string to double
            double height = Double.parseDouble(heightText);
            // get selected unit: cm and inches
            String selectedUnit = (String) unitsDropdown.getSelectedItem();

            // based on selected unit do
            switch (selectedUnit) {
                // if cm - store height as cm
                case "in" ->
                    storeHeight.setHeightInInch(height);
                // if inches  - store height as inches
                case "cm" ->
                    storeHeight.setHeightInCm(height);
            }

            // get selection if back hurts as true or false
            boolean isBackChecked = backTroubleCheckBox.isSelected();
            // get selection if there is heart issues as true or false
            boolean isHeartChecked = heartTroubleCheckBox.isSelected();

            // if heigh is allowed and no backpian and no heart issues
            if (storeHeight.isHightAllowed() && !isBackChecked && !isHeartChecked) {
                // show message that ride is allowed. Take message based on lang setting
                messageLabel.setText(messages.getString("conditions_met"));
                // set result as true
                this.result = true;
            } else {
                // create a message that ride is is not allowed and format it using min height, max height, unit. 
                // Take message based on lang setting
                String errorMessage = String.format(messages.getString("conditions_not_met"),
                        storeHeight.getMinHeight(), selectedUnit, storeHeight.getMaxHeight(), selectedUnit);
                // show the message as html to wrap
                messageLabel.setText("<html>" + errorMessage + "<html>");
                // set result as false
                this.result = false;
            }
        } catch (NumberFormatException e) {
            // if can convert input height to number and exception is thrown set text to empty string
            messageLabel.setText("");
            // set result to null
            this.result = null;
            // show dialog about the error and use lang setting to show the message ine ng or french
            JOptionPane.showMessageDialog(null, messages.getString("invalid_input"),
                    messages.getString("invalid_title"), JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_calculateBtnActionPerformed

    /**
     * Handle reset button
     *
     * @param evt
     */
    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        // set unit to cm (0 index)
        unitsDropdown.setSelectedIndex(0);
        // clean input height 
        inputHight.setText("");
        // set checkbox back trouble to false
        backTroubleCheckBox.setSelected(false);
        // set heart issue checkbox to false
        heartTroubleCheckBox.setSelected(false);
        // set result message to empty
        messageLabel.setText("");
        // set result to null - no result
        this.result = null;
    }//GEN-LAST:event_resetBtnActionPerformed

    /**
     * Handle exit button
     *
     * @param evt
     */
    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // set OptionPane no button according to lang
        UIManager.put("OptionPane.noButtonText", messages.getString("no"));
        // set OptionPane yes button according to lang
        UIManager.put("OptionPane.yesButtonText", messages.getString("yes"));
        // show confirmation dialog using user lang
        int option = javax.swing.JOptionPane.showConfirmDialog(this, messages.getString("exit_msg"),
                messages.getString("exit_title"),
                JOptionPane.YES_NO_OPTION);

        // if user selected - yes exit the program
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_exitBtnActionPerformed

    /**
     * Handle lang button
     *
     * @param evt
     */
    private void langBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langBtnActionPerformed
        // if was end - select french
        if (Locale.getDefault().equals(Locale.ENGLISH)) {
            Locale.setDefault(Locale.FRENCH);
        } else {
            // if it was french - selected english
            Locale.setDefault(Locale.ENGLISH);
        }
        // get messages for new lang
        messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

        // set title
        setTitle(messages.getString("title"));
        // set calculate label
        calculateBtn.setText(messages.getString("calculate_button"));
        // set height label
        heightLabel.setText(messages.getString("height_label"));
        // set back trouble label
        backTroubleLabel.setText(messages.getString("back_trouble_label"));
        // set heart trouble label
        heartTroubleLabel.setText(messages.getString("heart_trouble_label"));
        // set options in drop down box
        unitsDropdown.setModel(new DefaultComboBoxModel<>(new String[]{
            messages.getString("units_dropdown_cm"),
            messages.getString("units_dropdown_inches")
        }));
        // send language button label
        langBtn.setText(messages.getString("change_language_button"));
        // set reset button label
        resetBtn.setText(messages.getString("reset_button"));
        // set exit button label
        exitBtn.setText(messages.getString("exit_button"));

        // if result is there - update result message
        if (this.result != null) {
            if (this.result == false) {
                // set height unit
                String selectedUnit = (String) unitsDropdown.getSelectedItem();
                //  create a message that ride is is not allowed and format it using min height, max height, unit. 
                // Take message based on lang setting
                messageLabel.setText("<html>" + String.format(messages.getString("conditions_not_met"),
                        (double) storeHeight.getMinHeight(),
                        selectedUnit,
                        (double) storeHeight.getMaxHeight(),
                        selectedUnit) + "</html>");
            } else {
                // if ride allow update message using new lang
                messageLabel.setText(messages.getString("conditions_met"));
            }
        }
    }//GEN-LAST:event_langBtnActionPerformed

    /**
     * Handle unit selection dropdown. When unit is updated - remove height
     *
     * @param evt
     */
    private void unitsDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unitsDropdownActionPerformed
        // clean message
        messageLabel.setText("");
        // clean height input
        inputHight.setText("");
        // clean result
        this.result = null;
    }//GEN-LAST:event_unitsDropdownActionPerformed

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
            java.util.logging.Logger.getLogger(RollerCoasterRide.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RollerCoasterRide.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RollerCoasterRide.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RollerCoasterRide.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RollerCoasterRide().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox backTroubleCheckBox;
    private javax.swing.JLabel backTroubleLabel;
    private javax.swing.JButton calculateBtn;
    private javax.swing.JButton exitBtn;
    private javax.swing.JCheckBox heartTroubleCheckBox;
    private javax.swing.JLabel heartTroubleLabel;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField inputHight;
    private javax.swing.JButton langBtn;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JButton resetBtn;
    private javax.swing.JComboBox<String> unitsDropdown;
    // End of variables declaration//GEN-END:variables
}