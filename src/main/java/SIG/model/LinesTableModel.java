/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SIG.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class LinesTableModel extends AbstractTableModel {

    private ArrayList<InvoiceLine> invoicesLines;
    private String[] linesColumns = {"NO", "Item Name", "Item Price", "Count", "Item Total"};

    public LinesTableModel(ArrayList<InvoiceLine> invoicesLines) {
        this.invoicesLines = invoicesLines;
    }

    public ArrayList<InvoiceLine> getInvoicesLines() {
        return invoicesLines;
    }

    public String[] getLinesColumns() {
        return linesColumns;
    }

    @Override
    public int getRowCount() {
        return invoicesLines.size();
    }

    @Override
    public int getColumnCount() {
        return linesColumns.length;
    }

    @Override
    public String getColumnName(int col) {
        return linesColumns[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = invoicesLines.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return line.getInvoice().getNum();
            case 1:
                return line.getName();
            case 2:
                return line.getPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotalLines();
            default:
                return "";
        }
    }

}
