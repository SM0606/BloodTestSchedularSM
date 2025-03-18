/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package smbloodtestscheduler;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author sarah
 */
public class BloodTestSchedulerGUISM extends javax.swing.JFrame {

    /**
     * Creates new form BloodTestSchedulerGUISM
     */
    public BloodTestSchedulerGUISM() {
        initComponents();
    }
    public static void displayPatientDetails() {
    // Assuming these are the client details you're saving
    String clientName = NameTF.getText().trim();
    String clientAge = AgeTF.getText().trim();
    String gpDetails = GPDetailsTF.getText().trim();
    String urgency = (String) LevelCB.getSelectedItem();
    String hospitalWard = (String) TrueFalseCB.getSelectedItem();

    // Check if the fields are empty before displaying
    if (clientName.isEmpty() || clientAge.isEmpty() || gpDetails.isEmpty() || urgency.isEmpty() || hospitalWard.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please provide all details first.", "Error", JOptionPane.ERROR_MESSAGE);
        return;  // Exit the method if details are missing
    }

    // Prepare the details to display
    String patientDetails = "Name: " + clientName + "\n"
                            + "Age: " + clientAge + "\n"
                            + "GP Details: " + gpDetails + "\n"
                            + "Urgency: " + urgency + "\n"
                            + "Hospital Ward: " + hospitalWard;

    // Display the details in a message dialog
    JOptionPane.showMessageDialog(null, patientDetails, "Patient Details", JOptionPane.INFORMATION_MESSAGE);
}
    public static void addPatient() {
    // Make sure to call the validation method first before proceeding
    NameChecker();  // This checks Name, Age, and GP Details

    // Get user input from fields after validation
    String name = NameTF.getText();
    String priority = (String) LevelCB.getSelectedItem(); // Get selected priority
    String ageText = AgeTF.getText(); // Age input as String
    boolean fromHospitalWard = TrueFalseCB.getSelectedItem().equals("Yes"); // Check if selected "Yes"
    String gpDetails = GPDetailsTF.getText();

    // Check if the age input is empty or invalid
    if (ageText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method if the age is empty
    }

    // Check if the ageText contains only numbers (it can be checked via regex)
    if (!ageText.matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method if the age is invalid
    }

    // Parse the age if it's a valid number
    int age = Integer.parseInt(ageText);

    // Create a new Person object with the user input
    Person newPerson = new Person(name, priority, age, fromHospitalWard, gpDetails);

    // Add the new Person to the list (queue, priority queue, or wherever needed)
    SmBloodTestScheduler.addPerson(newPerson);  // Assuming this method adds to your lists

    // Update the display area to show all registered patients
    QueueTa.setText(SmBloodTestScheduler.getAllPatientsList()); // Refresh the display area

    // Clear the input fields for the next entry
    NameTF.setText("");
    AgeTF.setText("");
    GPDetailsTF.setText("");
    TrueFalseCB.setSelectedIndex(0);  // Reset to "No"
    LevelCB.setSelectedIndex(0);  // Reset to "Urgent"
}
    public static void NameChecker() {
    // Get the name input from the text field
    String ClientName = BloodTestSchedulerGUISM.NameTF.getText().trim(); // Trim whitespace around the name

    // Check if the name is empty
    if (ClientName.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Provide a Name!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.NameTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.NameTF.setText(""); // Clear the field
        return; // Exit the method
    }

    // Check if the name contains only letters
    if (!ClientName.matches("[a-zA-Z]+")) {
        JOptionPane.showMessageDialog(null, "Letters only for the Name!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.NameTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.NameTF.setText(""); // Clear the field
        return; // Exit the method
    }

    // Check if the name length is more than 20 characters
    if (ClientName.length() > 20) {
        JOptionPane.showMessageDialog(null, "20 characters max for the Name!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.NameTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.NameTF.setText(""); // Clear the field
        return; // Exit the method
    }

    // Age Validation (same logic for age)
    String ClientAge = BloodTestSchedulerGUISM.AgeTF.getText().trim();

    if (ClientAge.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Provide an Age!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.AgeTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.AgeTF.setText(""); // Clear the field
        return; // Exit the method
    }

    if (!ClientAge.matches("[0-9]+")) {
        JOptionPane.showMessageDialog(null, "Numbers only for the Age!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.AgeTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.AgeTF.setText(""); // Clear the field
        return; // Exit the method
    }

    if (ClientAge.length() > 2) {
        JOptionPane.showMessageDialog(null, "2 characters max for the Age!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.AgeTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.AgeTF.setText(""); // Clear the field
        return; // Exit the method
    }

    // GP Details Validation
    String DPDetails = BloodTestSchedulerGUISM.GPDetailsTF.getText().trim();

    if (DPDetails.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Provide GP Details!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.GPDetailsTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.GPDetailsTF.setText(""); // Clear the field
        return; // Exit the method
    }

    if (!DPDetails.matches("[a-zA-Z]+")) {
        JOptionPane.showMessageDialog(null, "Letters only for the Details!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.GPDetailsTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.GPDetailsTF.setText(""); // Clear the field
        return; // Exit the method
    }

    if (DPDetails.length() > 20) {
        JOptionPane.showMessageDialog(null, "20 characters max for the Details!", "Error", JOptionPane.ERROR_MESSAGE);
        BloodTestSchedulerGUISM.GPDetailsTF.setBackground(new Color(227, 255, 246));
        BloodTestSchedulerGUISM.GPDetailsTF.setText(""); // Clear the field
        return; // Exit the method
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

        jPanel1 = new javax.swing.JPanel();
        TitleJLB = new javax.swing.JLabel();
        NameJLB = new javax.swing.JLabel();
        AgeJLB = new javax.swing.JLabel();
        PrioretyJLB = new javax.swing.JLabel();
        HospitalJLB = new javax.swing.JLabel();
        GPDetailsJLB = new javax.swing.JLabel();
        NameTF = new javax.swing.JTextField();
        LevelCB = new javax.swing.JComboBox<>();
        AgeTF = new javax.swing.JTextField();
        TrueFalseCB = new javax.swing.JComboBox<>();
        GPDetailsTF = new javax.swing.JTextField();
        AddBTN = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        QueueJLB = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        QueueTa = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        CurrentPatientTA = new javax.swing.JTextArea();
        NextBTN = new javax.swing.JButton();
        NoShowBTN = new javax.swing.JButton();
        NoShowJLB = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        NoShowTA = new javax.swing.JTextArea();
        DisplayBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TitleJLB.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        TitleJLB.setText("Add New Patient");

        NameJLB.setText("Name:");

        AgeJLB.setText("Age:");

        PrioretyJLB.setText("Priorety Level:");

        HospitalJLB.setText("From Hospital Ward:");

        GPDetailsJLB.setText("GP Details:");

        NameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameTFActionPerformed(evt);
            }
        });

        LevelCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "Urgent" }));

        AgeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgeTFActionPerformed(evt);
            }
        });

        TrueFalseCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "True", "False" }));

        AddBTN.setText("Add Patient");
        AddBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBTNActionPerformed(evt);
            }
        });

        QueueJLB.setText("Current Queue List:");

        QueueTa.setColumns(20);
        QueueTa.setRows(5);
        jScrollPane1.setViewportView(QueueTa);

        CurrentPatientTA.setColumns(20);
        CurrentPatientTA.setRows(5);
        jScrollPane2.setViewportView(CurrentPatientTA);

        NextBTN.setText("Next Patient");
        NextBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NextBTNMouseClicked(evt);
            }
        });

        NoShowBTN.setText("Mark as No-Show");
        NoShowBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NoShowBTNMouseClicked(evt);
            }
        });
        NoShowBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoShowBTNActionPerformed(evt);
            }
        });

        NoShowJLB.setText("List Of No-Shows:");

        NoShowTA.setColumns(20);
        NoShowTA.setRows(5);
        jScrollPane3.setViewportView(NoShowTA);

        DisplayBTN.setText("Display");
        DisplayBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisplayBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(NameJLB)
                                .addGap(18, 18, 18)
                                .addComponent(NameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(PrioretyJLB)
                                .addGap(18, 18, 18)
                                .addComponent(LevelCB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(TitleJLB))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AgeJLB)
                        .addGap(27, 27, 27)
                        .addComponent(AgeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(HospitalJLB)
                        .addGap(18, 18, 18)
                        .addComponent(TrueFalseCB, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GPDetailsJLB)
                        .addGap(18, 18, 18)
                        .addComponent(GPDetailsTF, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AddBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DisplayBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(73, 73, 73))
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(QueueJLB)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(NoShowJLB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NextBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(NoShowBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleJLB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(NameJLB)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(PrioretyJLB)
                                    .addComponent(LevelCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(AddBTN)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AgeJLB)
                            .addComponent(AgeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HospitalJLB)
                            .addComponent(TrueFalseCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DisplayBTN))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GPDetailsJLB)
                            .addComponent(GPDetailsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(NameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QueueJLB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(NextBTN)
                        .addGap(18, 18, 18)
                        .addComponent(NoShowBTN)
                        .addGap(29, 29, 29)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(NoShowJLB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameTFActionPerformed

    private void AgeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgeTFActionPerformed

    private void AddBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBTNActionPerformed

        addPatient(); // Call addPatient method when button is clicked
    }//GEN-LAST:event_AddBTNActionPerformed

    private void DisplayBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisplayBTNActionPerformed
       SmBloodTestScheduler.displayClients();
      
    }//GEN-LAST:event_DisplayBTNActionPerformed

    private void NextBTNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextBTNMouseClicked
     SmBloodTestScheduler.getNextPatient();
    }//GEN-LAST:event_NextBTNMouseClicked

    private void NoShowBTNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NoShowBTNMouseClicked
    SmBloodTestScheduler.NoShows();
    }//GEN-LAST:event_NoShowBTNMouseClicked

    private void NoShowBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoShowBTNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoShowBTNActionPerformed

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
            java.util.logging.Logger.getLogger(BloodTestSchedulerGUISM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BloodTestSchedulerGUISM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BloodTestSchedulerGUISM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BloodTestSchedulerGUISM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BloodTestSchedulerGUISM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBTN;
    private javax.swing.JLabel AgeJLB;
    public static javax.swing.JTextField AgeTF;
    public static javax.swing.JTextArea CurrentPatientTA;
    private javax.swing.JButton DisplayBTN;
    private javax.swing.JLabel GPDetailsJLB;
    public static javax.swing.JTextField GPDetailsTF;
    private javax.swing.JLabel HospitalJLB;
    public static javax.swing.JComboBox<String> LevelCB;
    private javax.swing.JLabel NameJLB;
    public static javax.swing.JTextField NameTF;
    private javax.swing.JButton NextBTN;
    private javax.swing.JButton NoShowBTN;
    private javax.swing.JLabel NoShowJLB;
    public static javax.swing.JTextArea NoShowTA;
    private javax.swing.JLabel PrioretyJLB;
    private javax.swing.JLabel QueueJLB;
    public static javax.swing.JTextArea QueueTa;
    private javax.swing.JLabel TitleJLB;
    public static javax.swing.JComboBox<String> TrueFalseCB;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
