package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InvoiceController {
    public void FindInvoice(JTextField invoiceNumber, JLabel invoiceNumberFinded, JLabel dateInvoiceFinded, JLabel customerFirstName, JLabel customerLastName,JLabel customerDni){
        
        Config.CConnection connection = new Config.CConnection();
        try {
            String sql = "SELECT invoice.idInvoice, invoice.invoiceDate, customer.firstName, customer.lastName, customer.dni FROM invoice "
                + "INNER JOIN customer ON customer.idCustomer = invoice.idCustomer where invoice.idInvoice=?;";
                    
             PreparedStatement ps = connection.connection().prepareStatement(sql);
             ps.setInt(1, Integer.parseInt(invoiceNumber.getText()));
             ResultSet rs = ps.executeQuery();
             
             if(rs.next()){
                 invoiceNumberFinded.setText(String.valueOf(rs.getInt("idInvoice")));
                 dateInvoiceFinded.setText(rs.getDate("invoiceDate").toString());
                 customerFirstName.setText(rs.getString("firstName"));
                 customerLastName.setText(rs.getString("lastName"));
                 customerDni.setText(rs.getString("dni"));
             }else {
                 invoiceNumberFinded.setText("");
                 dateInvoiceFinded.setText("");
                 customerFirstName.setText("");
                 customerLastName.setText("");
                 customerDni.setText("");
                 
                 JOptionPane.showMessageDialog(null, "No se encontró la factura!");
             }
             
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al buscar factura: " + e);
        } finally {
            connection.disconnect();
        }
    }
    
    public void findInvoiceDetail(JTextField invoiceNumber, JTable invoiceTable, JLabel IVA, JLabel total){
        
        Config.CConnection connection = new Config.CConnection();
        DefaultTableModel invoiceModel = new DefaultTableModel();
        
        invoiceModel.addColumn("N. Producto");
        invoiceModel.addColumn("Cantidad");
        invoiceModel.addColumn("Precio Venta");
        invoiceModel.addColumn("Subtotal");
        
        invoiceTable.setModel(invoiceModel);
        
        try {
            String sql = "SELECT product.productName, invoicedetail.quantity, invoicedetail.price FROM invoicedetail " +
                         "INNER JOIN invoice ON invoice.idInvoice = invoicedetail.idInvoice " +
                         "INNER JOIN product ON product.idProduct = invoicedetail.idProduct " +
                         "WHERE invoice.idInvoice = ?;";
            
            PreparedStatement ps = connection.connection().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(invoiceNumber.getText()));
            
            ResultSet rs = ps.executeQuery();
            double invoiceTotal = 0;
            double ivaValue = 0.18;
            
            
            
            while(rs.next()){
                String productName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double subTotal = quantity * price;
                invoiceTotal += subTotal;
            
                invoiceModel.addRow(new Object[]{
                    productName,
                    quantity,
                    price,
                    subTotal
                });
            }
            
            DecimalFormat format = new DecimalFormat("#,##0.00"); 
            IVA.setText("$" + format.format(invoiceTotal * ivaValue));
            total.setText("$" + format.format(invoiceTotal));
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el detalle de factura" + e.toString());
        } finally {
            connection.disconnect();
        }

        
        
        
    }
    
}
