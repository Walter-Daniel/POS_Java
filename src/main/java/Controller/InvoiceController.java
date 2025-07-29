package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
    
}
