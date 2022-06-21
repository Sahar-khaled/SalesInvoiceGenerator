package SIG.controller;

import SIG.VIEW.InvoiceFrame;
import SIG.model.InvoiceHeader;
import SIG.model.InvoiceLine;
import SIG.model.InvoicesTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author DELL
 */
public class FileOperations implements ActionListener {

    private InvoiceFrame frame;

    public FileOperations(InvoiceFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ACTION HANDLER CAlled");
        String selectAction = e.getActionCommand();
        if (selectAction == "Load File") {
            loadFile();
            System.out.println("the action is  " + selectAction);
        } else if (selectAction == "Save File") {
            saveFile();
            System.out.println("the action is " + selectAction);
        } else {
            System.out.println("Error");
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
                System.out.println("Invoice Table File Have Successfully Added");

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
                click = fileChooser.showOpenDialog(frame);
                if (click == JFileChooser.APPROVE_OPTION) {
                    File line = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(line.getAbsolutePath());
                    List<String> linesOfLines = Files.readAllLines(linePath);
                    System.out.println("Item Table File Have Successfully Added");
                    List<InvoiceLine> AllItems = new ArrayList<>();

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
                        AllItems.add(l);

                    }
                    int LastNumberLine = 0;
                    for (InvoiceLine lineItem : AllItems) {
                        if (LastNumberLine != lineItem.getInvoice().getNum()) {
                            System.out.println("******** - Invoice Number " + lineItem.getInvoice().getNum() + " - *******");
                            System.out.println("*************************************");
                            System.out.println(lineItem.getInvoice().getDate() + "," + lineItem.getInvoice().getCustomer());

                            //     Print all items in this invoice
                            for (InvoiceLine SubItem : AllItems) {
                                if (SubItem.getInvoice().getNum() == lineItem.getInvoice().getNum()) {
                                    System.out.println("Item Name : " + SubItem.getName() + "   &&   Item Price : " + SubItem.getPrice() + "   &&   ItemCount : " + SubItem.getCount());
                                }
                            }

                            System.out.println("*************************************");
                            LastNumberLine = lineItem.getInvoice().getNum();

                        }

                    }

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

}
