package SIG.controller;

import SIG.VIEW.InvoiceFrame;
import SIG.VIEW.NewInvoiceDialog;
import SIG.VIEW.NewLineDialog;
import SIG.model.InvoiceHeader;
import SIG.model.InvoiceLine;
import SIG.model.InvoicesTableModel;
import SIG.model.LinesTableModel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Date;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author DELL
 */
public class ActionHandler implements ActionListener, ListSelectionListener {

    private InvoiceFrame frame;
    private NewInvoiceDialog newInvoiceDialog;
    private NewLineDialog newLineDialog;

    public ActionHandler(InvoiceFrame frame) {
        this.frame = frame;
    }

//    private JFrame frame = new JFrame("");
//    private JTextField invoiceDate;
//    private JTextField customerName;
//    private JButton okBtn;
//    private JButton cancelBtn;
    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("ACTION HANDLER CAlled");
        switch (e.getActionCommand()) {
            case "New Invoice" -> {
                System.out.println("New Invoice");
                newInv();
                break;

            }
            case "Delete Invoice" -> {
                System.out.println("Delete Invoice ");
                deleteInv();
                break;

            }
            case "New Item" -> {
                System.out.println("New Item ");
                newItem();
                break;

            }
            case "Delete Item" -> {
                System.out.println("Delete Item  ");
                deleteItem();
                break;

            }
            case "Load File" -> {
                System.out.println("Load File  ");
                loadFile();
                break;

            }
            case "Save File" -> {
                System.out.println("Save File  ");
                saveFile();
                break;

            }
            case "start Invoice OK" -> {
                System.out.println("Invoice OK ");
                invoiceOK();
                break;

            }
            case "start Invoice Cancel" -> {
                System.out.println("invoice cancel ");
                invoiceCancel();
                break;
            }
            case "fire Line OK" -> {
                System.out.println("line ok ");
                lineOk();
                break;

            }
            case "fire Line Cancel" -> {
                System.out.println("line cancel ");
                lineCancel();
                break;

            }

        }
    }

    private void newInv() {
        newInvoiceDialog = new NewInvoiceDialog(frame);
        newInvoiceDialog.setVisible(true);

    }

    private void deleteInv() {
        int chosenRow = frame.getInvoiceHeaderTable().getSelectedRow();
        if (chosenRow != -1) {
            frame.getInvoices().remove(chosenRow);
            frame.getInvoicesTableModel().fireTableDataChanged();

        }

    }

    private void newItem() {
        newLineDialog = new NewLineDialog(frame);
        newLineDialog.setVisible(true);

    }

    private void deleteItem() {
        int chosenInvoice = frame.getInvoiceHeaderTable().getSelectedRow();
        int chosenItemRow = frame.getInvoiceLineTable().getSelectedRow();

        if (chosenInvoice != -1 && chosenItemRow != -1) {
            InvoiceHeader inv = frame.getInvoices().get(chosenInvoice);
            inv.getLines().remove(chosenItemRow);

            frame.getInvoices().remove(chosenItemRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
            LinesTableModel linesTableModel = new LinesTableModel(inv.getLines());
            frame.getInvoiceLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();

        }
    }

    private void loadFile() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int click = fileChooser.showOpenDialog(frame);
            if (click == JFileChooser.APPROVE_OPTION) {
                File header = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(header.getAbsolutePath());
                List<String> linesOfHeaders = Files.readAllLines(headerPath);
                System.out.println("read lines of invoices");
                ArrayList<InvoiceHeader> arrOfInvoices = new ArrayList<>();

                for (String oneHeaderLine : linesOfHeaders) {
                    String[] headersArr = oneHeaderLine.split(",");

                    int invNumber = Integer.parseInt(headersArr[0]);

                    String invDate = headersArr[1];

                    String invCustomerName = headersArr[2];

                    InvoiceHeader inv = new InvoiceHeader(
                            invNumber, invDate, invCustomerName);
                    arrOfInvoices.add(inv);

                }
                System.out.println("watch invoice");
                click = fileChooser.showOpenDialog(frame);
                if (click == JFileChooser.APPROVE_OPTION) {
                    File line = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(line.getAbsolutePath());
                    List<String> linesOfLines = Files.readAllLines(linePath);
                    System.out.println("read lines of invoices lines ");
                    for (String oneLine : linesOfLines) {
                        String linesArr[] = oneLine.split(",");
                        int invoiceNum = Integer.parseInt(linesArr[0]);
                        String name = linesArr[1];
                        double price = Double.parseDouble(linesArr[2]);
                        int count = Integer.parseInt(linesArr[3]);

                        InvoiceHeader invc = null;
                        for (InvoiceHeader invoiceHeader : arrOfInvoices) {
                            if (invoiceHeader.getNum() == invoiceNum) {
                                invc = invoiceHeader;
                                break;
                            }
                        }

                        InvoiceLine l = new InvoiceLine(
                                invc,
                                name,
                                price,
                                count
                        );
                        invc.getLines().add(l);
                    }
                    System.out.println("TEST lines ");

                }
                frame.setInvoices(arrOfInvoices);
                InvoicesTableModel invoicesTableModel = new InvoicesTableModel(arrOfInvoices);
                frame.setInvoicesTableModel(invoicesTableModel);
                frame.getInvoiceHeaderTable().setModel(invoicesTableModel);
                frame.getInvoicesTableModel().fireTableDataChanged();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoiceHeaders = frame.getInvoices();
        String invHeaders = "";
        String invLines = "";

        for (InvoiceHeader invoice : invoiceHeaders) {
            String invHeadersFormat = invoice.getInvoiceFormat();
            invHeaders += invHeadersFormat;
            invHeaders += "\n";

            for (InvoiceLine inv : invoice.getLines()) {
                String linessFormat = inv.getLineFormat();
                invLines += linessFormat;
                invLines += "\n";
            }
        }
        System.out.println("test format");
        try {
            JFileChooser fileChooser = new JFileChooser();
            int fileSaver = fileChooser.showSaveDialog(frame);
            if (fileSaver == JFileChooser.APPROVE_OPTION) {
                File invFile = fileChooser.getSelectedFile();
                FileWriter invFileWriter = new FileWriter(invFile);
                invFileWriter.write(invHeaders);
                invFileWriter.flush();
                invFileWriter.close();

                fileSaver = fileChooser.showSaveDialog(frame);
            }
            if (fileSaver == JFileChooser.APPROVE_OPTION) {
                File lineFile = fileChooser.getSelectedFile();
                FileWriter lineFileWriter = new FileWriter(lineFile);
                lineFileWriter.write(invLines);
                lineFileWriter.flush();
                lineFileWriter.close();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int chosenIndex = frame.getInvoiceHeaderTable().getSelectedRow();
        if (chosenIndex != -1) {
            System.out.println("selected row" + chosenIndex);
            InvoiceHeader presentInv = frame.getInvoices().get(chosenIndex);
            frame.getInvNumLabel().setText("" + presentInv.getNum());
            frame.getInvDateLabel().setText("" + presentInv.getDate());
            frame.getCustomerNameLabel().setText("" + presentInv.getCustomer());
            frame.getTotalLabel().setText("" + presentInv.getBillTotal());
            LinesTableModel linesTableModel = new LinesTableModel(presentInv.getLines());
            frame.getInvoiceLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();

        }
    }

    private void invoiceOK() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = newInvoiceDialog.getInvDateField().getText();
        String dateText = newInvoiceDialog.getInvDateField().getText();
        String customerN = newInvoiceDialog.getCustNameField().getText();
        int number = frame.getFollowingInvNumber();
        try {
            String[] splitDate = date.split("-");

            if (splitDate.length < 3 ) {
                JOptionPane.showMessageDialog(frame,
                        "please enter a valid date", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int checkDayFormat = Integer.parseInt(splitDate[0]);
                int checkMonthFormat = Integer.parseInt(splitDate[1]);
                int checkYearFormat = Integer.parseInt(splitDate[2]);
                if (checkDayFormat > 31 ||  checkMonthFormat >12  ){
                     JOptionPane.showMessageDialog(frame,
                        "please enter a valid date", "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                else {
                InvoiceHeader invHeader = new InvoiceHeader(number, dateText, customerN);
                frame.getInvoices().add(invHeader);
                frame.getInvoicesTableModel().fireTableDataChanged();
                newInvoiceDialog.setVisible(false);
                newInvoiceDialog.dispose();
                newInvoiceDialog = null;
            }}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "please enter a valid date", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void invoiceCancel() {
        newInvoiceDialog.setVisible(false);
        newInvoiceDialog.dispose();
        newInvoiceDialog = null;

    }

    private void lineOk() {
        String name = newLineDialog.getItemNameField().getText();
        String countToString = newLineDialog.getItemCountField().getText();
        String priceToString = newLineDialog.getItemPriceField().getText();
        int currentCount = Integer.parseInt(countToString);
        double currentPrice = Double.parseDouble(priceToString);
        int selectedBill = frame.getInvoiceHeaderTable().getSelectedRow();
        if (selectedBill != -1) {
            InvoiceHeader inv = frame.getInvoices()
                    .get(selectedBill);
            InvoiceLine invoiceLine = new InvoiceLine(inv, name, currentPrice, currentCount);
            inv.getLines().add(invoiceLine);
            LinesTableModel linesTableModel = (LinesTableModel) frame.getInvoiceLineTable().getModel();
//            linesTableModel.getInvoicesLines().add(invoiceLine);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableStructureChanged();

        }

        newLineDialog.setVisible(false);
        newLineDialog.dispose();
        newLineDialog = null;

    }

    private void lineCancel() {
        newLineDialog.setVisible(false);
        newLineDialog.dispose();
        newLineDialog = null;

    }

}
