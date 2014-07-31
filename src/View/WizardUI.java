/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.EnrolHeaders;
import Model.Parser;
import Model.Preferences;
import Model.UserHeaders;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

/**
 * A "Wizard" to assist the user with setting Preferences for conversion from
 * SMS to Moodle.  
 * 
 * @author mcnabba
 */
public class WizardUI extends javax.swing.JDialog {

    private Parser p;
    private JPanel centerPnl = new JPanel();
    private Preferences prefs;
    private JTable userTbl, enrolTbl;
    private JButton backBtn, nextBtn, cancelBtn;
    private JTextField uTxtFld;
    
    /**
     * Creates new form WizardUI
     */
    public WizardUI(Parser p) {
        initComponents();
        int row = 1;
        this.setTitle("Conversion Preferences Wizard");
        this.setSize(600, 600);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.p = p;
        this.prefs = p.getPrefs();
        this.setLayout(new java.awt.BorderLayout());
        
        JPanel topPnl = new JPanel();
        topPnl.setPreferredSize(new Dimension(WIDTH, 100));
        topPnl.setBackground(Color.white);
        JLabel titleLbl = new JLabel();
        titleLbl.setText("Create Preferences for Conversion.");
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        titleLbl.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel iconLbl = new JLabel();
        iconLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/spur-gear-icon.png")));
        topPnl.add(iconLbl);
        topPnl.add(titleLbl);
        this.add(topPnl, BorderLayout.NORTH);
        
        //Side Panel that lists the steps to complete the wizard.
        JPanel sidePnl = new JPanel();
        sidePnl.setBorder(new LineBorder(Color.white, 1));
        BoxLayout box = new javax.swing.BoxLayout(sidePnl, WIDTH);
        sidePnl.setLayout(box);
        JLabel userLbl = new JLabel();
        userLbl.setText("1. User Details");
        userLbl.setBorder(new EmptyBorder(5, 5, 5, 10));
        sidePnl.add(userLbl);
        JLabel enrolLbl = new JLabel("2. Enrolment Details");
        enrolLbl.setBorder(new EmptyBorder(5, 5, 5, 10));
        sidePnl.add(enrolLbl);
        JLabel courseLbl = new JLabel("3. Confirm and Save");
        courseLbl.setBorder(new EmptyBorder(5, 5, 5, 10));
        sidePnl.add(courseLbl);

        
        this.add(sidePnl, BorderLayout.WEST);
        
        //Creates center panel where preferences are set
        centerPnl.setBorder(new LineBorder(Color.white, 1));
        centerPnl.setLayout(new CardLayout());

        //creates user field card for matching user headers to moodle
        JPanel userCard = new JPanel(new java.awt.BorderLayout());
        JPanel userTopPnl = new JPanel(new java.awt.BorderLayout());
        JLabel uTopLbl = new JLabel(" Match the CSV Headers from your file to the Moodle Headers. ");
        userTopPnl.add(uTopLbl);
        userCard.add(userTopPnl, BorderLayout.NORTH);
        JPanel centerUserPnl = new JPanel(new java.awt.BorderLayout());
        userTbl = new JTable(new XMLTableModel());
        JComboBox uCB = new JComboBox(p.getHeaders());
        userTbl.getColumn("CSV Headers").setCellEditor(new DefaultCellEditor(uCB));
        centerUserPnl.add(userTbl, BorderLayout.CENTER);
        uTxtFld = new JTextField();
        uTxtFld.setToolTipText("Enter domain for email.");
//        uTxtFld.setVisible(true);
        uTxtFld.setEnabled(true);
        uTxtFld.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                prefs.setDomain(uTxtFld.getText());
            }
        });
        centerUserPnl.add(uTxtFld, BorderLayout.SOUTH);
        JPanel bottomUserPnl = new JPanel(new java.awt.FlowLayout());
        userCard.add(bottomUserPnl, BorderLayout.SOUTH);
        userCard.add(centerUserPnl, BorderLayout.CENTER);
        JCheckBox uChkBox = new JCheckBox("Confirm User modification?");
        uChkBox.addActionListener(   new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uChkBoxActionPerformed(e);
            }
        });
//        JCheckBox emailChkBox = new JCheckBox("Append domain to email?");
//        emailChkBox.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                emailChkBoxActionPerformed(e);
//            }
//        });
//        bottomUserPnl.add(emailChkBox);
        bottomUserPnl.add(uChkBox); 
        
        //creates enrollment field card for matching course headers to moodle
        JPanel enrolCard = new JPanel(new java.awt.BorderLayout());
        JPanel enrolTopPnl = new JPanel(new java.awt.BorderLayout());
        JPanel bottomEnrolPnl = new JPanel(new java.awt.BorderLayout());
        JLabel eTopLbl = new JLabel(" Match the CSV Headers from your file to the Moodle Headers. ");
        enrolTopPnl.add(eTopLbl);
        enrolCard.add(enrolTopPnl, BorderLayout.NORTH);
        JPanel centerEnrolPnl = new JPanel(new java.awt.BorderLayout());
        enrolTbl = new JTable(new XMLTableModel(EnrolHeaders.COHORT));
        JComboBox eCB = new JComboBox(EnrolHeaders.values());
        enrolTbl.getColumn("Moodle Headers").setCellEditor(new DefaultCellEditor(eCB));
        centerEnrolPnl.add(enrolTbl);
        JCheckBox eChkBox = new JCheckBox("Confirm Course modification?");
        eChkBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eChkBoxActionPerformed(e);
            }
        });
        bottomEnrolPnl.add(eChkBox);
        enrolCard.add(centerEnrolPnl, BorderLayout.CENTER);
        enrolCard.add(bottomEnrolPnl, BorderLayout.SOUTH);
        
        
        JPanel lastCard = new JPanel(new java.awt.BorderLayout());
        JTextArea lTxtFld = new JTextArea("This will save your chosen mappings from your"
                + " file to a preferences file that the converter will use to modify the data.  "
                + "In the case your file changes format, you will need to rerun this Wizard.");
        lTxtFld.setEditable(false);
        lTxtFld.setWrapStyleWord(true);
        lTxtFld.setLineWrap(true);
        JButton lBtn = new JButton("Save");
        lBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    prefs.writePreferences();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(WizardUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
        lastCard.add(lTxtFld, BorderLayout.CENTER);
        lastCard.add(lBtn, BorderLayout.SOUTH);
        
        centerPnl.add(userCard);
        centerPnl.add(enrolCard);
        centerPnl.add(lastCard);
        this.add(centerPnl, BorderLayout.CENTER);
        
        //Bottom toolbar that contains Cancel, Next, and Previous buttons
        JToolBar bottomBar = new JToolBar();
        bottomBar.setFloatable(false);
        bottomBar.setBackground(Color.white);
        bottomBar.setPreferredSize(new Dimension(WIDTH, 50));
        bottomBar.setLayout(new java.awt.GridLayout());
        backBtn = new JButton("< Previous");
        backBtn.setPreferredSize(new Dimension(60, 30));
        backBtn.addActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(ActionEvent e)  {
                backCard(e);
            }
        });
        
        nextBtn = new JButton("Next >");
        nextBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextCard(e);
            }
        });
        
        nextBtn.setPreferredSize(new Dimension(60, 30));
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        cancelBtn.setPreferredSize(new Dimension(60, 30));
        bottomBar.add(cancelBtn);
        bottomBar.add(Box.createHorizontalGlue());
        bottomBar.add(backBtn);
        bottomBar.add(nextBtn);
        this.add(bottomBar, BorderLayout.SOUTH);
    }
    
    private void nextCard(ActionEvent e) {
        CardLayout cl = (CardLayout) centerPnl.getLayout();
        cl.next(centerPnl);
    }
    
    private void backCard(ActionEvent e)    {
        CardLayout cl = (CardLayout) centerPnl.getLayout();
        cl.previous(centerPnl);
    }
    
    private void uChkBoxActionPerformed(ActionEvent e)   {
        JCheckBox uChkBox = (JCheckBox) e.getSource();
         if (uChkBox.isSelected())   {
             userTbl.setEnabled(false);
             prefs.setUserHeaders(userTbl.getModel());
             prefs.setDomain(uTxtFld.getText());
         }   else   {
             userTbl.setEnabled(true);
         }
    }
    
//    private void emailChkBoxActionPerformed(ActionEvent e)  {
//        JCheckBox eChkBox = (JCheckBox) e.getSource();
//        if (eChkBox.isSelected())   {
//            uTxtFld.setEnabled(true);
//        }   else    {
//            uTxtFld.setEnabled(false);
//        }
//        
//    }
    
    private void eChkBoxActionPerformed(ActionEvent e)   {
        JCheckBox eChkBox = (JCheckBox) e.getSource();
        if (eChkBox.isSelected())   {
            enrolTbl.setEnabled(false);
            prefs.setCourseHeaders(enrolTbl.getModel());
        }   else    {
            enrolTbl.setEnabled(true);
        }
    }
    
    /**
     * class to populate the tables 
     */
      private class XMLTableModel extends AbstractTableModel{

          private String[][] table;
          private String[] colNames = {"CSV Headers", "Moodle Headers"};
          private static final int COL = 2;
          private int row;
          private int editCol;
          
        public XMLTableModel() {
            this.row = UserHeaders.values().length;
            this.editCol = 0;
            this.table = new String[row][COL];
            for (int i = 0; i < row; i++)  {
                table[i][1] = UserHeaders.values()[i].toString();
            }
        }
       
        public XMLTableModel(EnrolHeaders e) {
            this.row = p.getHeaders().length;
            this.editCol = 1;
            this.table = new String[row][COL];
            for (int i = 0; i < row; i++)  {
                table[i][0] = p.getHeaders()[i];
            }
        }

        @Override
        public String getColumnName(int col)   {
            return colNames[col];
        }

        @Override
        public int getRowCount() {
            return row;
        }

        @Override
        public int getColumnCount() {
            return COL;
        }

        @Override
        public Object getValueAt(int row, int col) {
            return table[row][col];
        }

        @Override
        public void setValueAt(Object value, int row, int col)  {
            if ( value != null)  {
                 table[row][col] = (String) value.toString();
                 this.fireTableCellUpdated(row, col);
            }
        }

        @Override
        public boolean isCellEditable(int row, int col)
        {
            if ( col == editCol)   {
                return true;
            }  
            return false;
        }
          
        public String[][] getTable()  {
            return this.table;
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
