package SIG.VIEW;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import SIG.VIEW.InvoiceFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class NewInvoiceDialog extends JDialog {
    private JTextField CustomerName;
    private JTextField invoiceDate;
    private JLabel customerNameLabel;
    private JLabel billDateLabel;
    private JButton okBtn;
    private JButton cancelBtn;

    public NewInvoiceDialog(InvoiceFrame frame) {
        customerNameLabel = new JLabel("Customer Name:");
        CustomerName = new JTextField(20);
        billDateLabel = new JLabel("Invoice Date:");
        invoiceDate = new JTextField(20);
        okBtn = new JButton(" OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("start Invoice OK");
        cancelBtn.setActionCommand("start Invoice Cancel");
        
        okBtn.addActionListener(frame.getHandler());
        cancelBtn.addActionListener(frame.getHandler());
        setLayout(new GridLayout(3, 2));
        
        add(billDateLabel);
        add(invoiceDate);
        add(customerNameLabel);
        add(CustomerName);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }

    public JTextField getCustNameField() {
        return CustomerName;
    }

    public JTextField getInvDateField() {
        return invoiceDate;
    }
    
}
