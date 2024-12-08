package ovs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * File Name:	SleepCalculator 
 * Programmer:	Steven Passynkov 
 * Date:	11 Feb 2024
 * Description:	This Swing application calculates the number of hours a person
 * has spent sleeping from the time the person born until today or custom start 
 * date and number of days the person is alive.
 */
public class SleepCalculator extends javax.swing.JFrame {

    // Date form that is displayed.
    static private String DATE_FORMAT = "YYYY/MM/DD";
    
    // create a SimpleDateFormat object for parsing dob that user
    // should enter. Use year(4 digits)/month(2 digits)/day(2 digits)
    // format
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * Creates new form SleepCalculator
     */
    public SleepCalculator() {
        initComponents();
        
        // make  dateLabel invisible 
        dateLabel.setVisible(false);
        // make  dateInput invisible 
        dateInput.setVisible(false);

        // do not allow to pass incorrect/out of range date. Like 2019/09/33
        dateFormat.setLenient(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        calculateBtn = new javax.swing.JButton();
        dobInput = new javax.swing.JTextField();
        dobLabel = new javax.swing.JLabel();
        exitBtn = new javax.swing.JButton();
        daysDifferenceDisplay = new javax.swing.JTextField();
        hoursOfSleepDisplay = new javax.swing.JTextField();
        jCheckBox = new javax.swing.JCheckBox();
        dateInput = new javax.swing.JTextField();
        dateLabel = new javax.swing.JLabel();
        clearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(51, 153, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Sleep Calculator");

        calculateBtn.setBackground(new java.awt.Color(51, 153, 255));
        calculateBtn.setForeground(new java.awt.Color(255, 255, 255));
        calculateBtn.setText("Calculate");
        calculateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateBtnActionPerformed(evt);
            }
        });

        dobInput.setText("YYYY/MM/DD");

        dobLabel.setLabelFor(dobInput);
        dobLabel.setText("Date of birth:");

        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        daysDifferenceDisplay.setEditable(false);
        daysDifferenceDisplay.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        daysDifferenceDisplay.setEnabled(false);

        hoursOfSleepDisplay.setActionCommand("<Not Set>");
        hoursOfSleepDisplay.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        hoursOfSleepDisplay.setEnabled(false);

        jCheckBox.setText("Is Custom Start Date?");
        jCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActionPerformed(evt);
            }
        });

        dateInput.setText("YYYY/MM/DD");

        dateLabel.setLabelFor(dobInput);
        dateLabel.setText("Start Date:");

        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(calculateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dobLabel)
                            .addComponent(dateLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateInput, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(title)
                                    .addComponent(dobInput, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBox)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(daysDifferenceDisplay)
                    .addComponent(hoursOfSleepDisplay)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {calculateBtn, exitBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(title)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dobLabel)
                    .addComponent(dobInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateLabel))
                .addGap(30, 30, 30)
                .addComponent(daysDifferenceDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hoursOfSleepDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitBtn)
                    .addComponent(calculateBtn)
                    .addComponent(clearBtn))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {calculateBtn, exitBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Get start date either today or custom if user selected checkbox
     *
     * @return Date
     * @throws ParseException Throw if data is incorrect
     */
    private Date getTodayOrCustomDate() throws ParseException {
        // if checkbox is selected
        if (jCheckBox.isSelected()) {
            // get text from dateInput and parse as Date
            Date date = dateFormat.parse(dateInput.getText());
            // return date
            return date;
        } else {
            // Get Calendar object
            Calendar calendar = Calendar.getInstance();
            // Get current date using Calendar object
            Date currentDate = calendar.getTime();
            // return current date
            return currentDate;

        }
    }

    /**
     * Event listener for calculate button. It does all calculation for number
     * of days and hours and show results.
     *
     * @param evt
     */
    private void calculateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateBtnActionPerformed
        try {
            // get text from dobInput and parse it. Create java Date object
            // for date of birth
            Date dob = dateFormat.parse(dobInput.getText());
            // Get today or custom start date
            Date startDate = this.getTodayOrCustomDate();
            // Check that dob date is after currentDate
            if (dob.after(startDate)) {
                // if not - show an error in JOptionPane as error
                javax.swing.JOptionPane.showMessageDialog(this, "Please enter dob before start/today date", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // calculate number of milliseconds between startDate and dob
            long timeDifferenceMillis = startDate.getTime() - dob.getTime();
            // convert milliseconds to days
            // 1 day=60 (seconds in hour) * 60 (minutes per hour) * 24 hours * 1000 (milliseconds to seconds)
            int daysDifference = (int) (timeDifferenceMillis / (1000 * 60 * 60 * 24));
            // each day person sleep 8 hours. Multiple days to 8 to find hours
            int hoursOfSleep = daysDifference * 8;

            // Get NumberFormat that uses US locale
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("en_US"));
            // Create message with number of days and set it into daysDifferenceDisplay field
            daysDifferenceDisplay.setText(String.format("You have been alive for: %s day(s).", numberFormat.format(daysDifference)));
            // Create a message with number of hours and set it into hoursOfSleepDisplay field
            hoursOfSleepDisplay.setText(String.format("You have slept: %s hours.", numberFormat.format(hoursOfSleep)));

        } catch (ParseException ex) {
            // ParseException is thrown in date is incorrect
            // show error message in JOptionPane
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid date format. Please use " + DATE_FORMAT, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_calculateBtnActionPerformed

    /**
     * Event listener for exit button.
     *
     * @param evt
     */
    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // show confirmation message asking if user wants to exit
        int option = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        // if yes - exit the program
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_exitBtnActionPerformed

    /**
     * Event listen when checkbox button is clicked
     *
     * @param evt
     */
    private void jCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxActionPerformed
        // if checkbox is selected 
        if (jCheckBox.isSelected()) {
            // make custom dateLabel visiable
            dateLabel.setVisible(true);
            // make custom dateInput visiable
            dateInput.setVisible(true);
        } else {  // if checkbox is not selected 
            // make custom dateLabel not visiable
            dateLabel.setVisible(false);
            // make custom dateInput not visiable
            dateInput.setVisible(false);
        }
    }//GEN-LAST:event_jCheckBoxActionPerformed

    /**
     * Event listen when Clean button is clicked
     *
     * @param evt
     */
    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        // set dobInput plaveholder text
        dobInput.setText(SleepCalculator.DATE_FORMAT);
        // set dateInput plaveholder text
        dateInput.setText(SleepCalculator.DATE_FORMAT);
        // clean hoursOfSleepDisplay text
        hoursOfSleepDisplay.setText("");
        // clean daysDifferenceDisplay text
        daysDifferenceDisplay.setText("");
    }//GEN-LAST:event_clearBtnActionPerformed

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
            java.util.logging.Logger.getLogger(SleepCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SleepCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SleepCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SleepCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SleepCalculator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JTextField dateInput;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextField daysDifferenceDisplay;
    private javax.swing.JTextField dobInput;
    private javax.swing.JLabel dobLabel;
    private javax.swing.JButton exitBtn;
    private javax.swing.JTextField hoursOfSleepDisplay;
    private javax.swing.JCheckBox jCheckBox;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
