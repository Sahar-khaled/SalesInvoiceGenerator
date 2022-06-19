
package SIG.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class InvoiceHeader {

    private int num;
    private String date;
    private String customer;
    private ArrayList<InvoiceLine> lines;
    private InvoicesTableModel invoicesTableModel;

    public InvoicesTableModel getInvoicesTableModel() {
        return invoicesTableModel;
    }

    public void setInvoicesTableModel(InvoicesTableModel invoicesTableModel) {
        this.invoicesTableModel = invoicesTableModel;
    }

    public InvoiceHeader(int num, String date, String customer) {
        this.num = num;
        this.date = date;
        this.customer = customer;
    }
    public double getBillTotal(){
        double gross = 0.0;
        for(InvoiceLine line : getLines()){
            gross += line.getTotalLines();
        };
        return gross;
    }
   

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + num + ", date=" + date + ", customer=" + customer + '}';
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<InvoiceLine> getLines() {
        if(lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    public String getInvoiceFormat () {
        return num + "," + date + "," + customer ;
        
    }
}
