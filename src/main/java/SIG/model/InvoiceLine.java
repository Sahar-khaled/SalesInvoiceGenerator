/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SIG.model;

/**
 *
 * @author DELL
 */
public class InvoiceLine {

    private InvoiceHeader invoice;
    private String name;
    private double price;
    private int count;

    public InvoiceLine(InvoiceHeader invoice, String name, double price, int count) {
        this.invoice = invoice;
        this.name = name;
        this.price = price;
        this.count = count;
    }

  

    public double getTotalLines() {
        return price * count;

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "invoice=" + invoice.getNum() + ", name=" + name + ", price=" + price + ", count=" + count + '}';
    }
    public String getLineFormat () {
        return invoice.getNum() + "," + name + "," + price + "," + count;
        
    }
}
