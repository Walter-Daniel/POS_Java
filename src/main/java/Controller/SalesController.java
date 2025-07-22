package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SalesController {
    
    public void findProduct(JTextField productName, JTable productTable){
        
        Config.CConnection connection = new Config.CConnection();
        Model.ProductModel product = new Model.ProductModel();
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Stock");
        
        productTable.setModel(model);
        
        try {
            String sql = "SELECT * FROM product WHERE product.productName like concat('%',?,'%')";
            PreparedStatement ps = connection.connection().prepareStatement(sql);
            ps.setString(1, productName.getText());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                product.setIdProduct(rs.getInt("idProduct"));
                product.setName(rs.getString("productName"));
                product.setPrice(rs.getDouble("productPrice"));
                product.setStock(rs.getInt("stock"));
                
                model.addRow(new Object[]{
                    product.getIdProduct(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
                });
            }
            
           productTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al mostrar producto" + e.toString());
        } finally {
            connection.disconnect();
        }
        
        for(int column = 0; column < productTable.getColumnCount(); column++){
            Class<?> columnClass = productTable.getColumnClass(column);
            productTable.setDefaultEditor(columnClass, null);
        }
        
    }
    
    public void selectProductSales(JTable productTable, JTextField id, JTextField productName, JTextField productPrice, JTextField stock, JTextField totalPrice){
     
        int row = productTable.getSelectedRow();
        
        try {
            
            if(row >= 0){
                id.setText(productTable.getValueAt(row, 0).toString());
                productName.setText(productTable.getValueAt(row, 1).toString());
                productPrice.setText(productTable.getValueAt(row, 2).toString());
                stock.setText(productTable.getValueAt(row, 3).toString());
                totalPrice.setText(productTable.getValueAt(row, 2).toString());
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
        }
    }
    
    public void findCustomer(JTextField customerName, JTable customerTable){
        
        Config.CConnection connection = new Config.CConnection();
        Model.CustomerModel customer = new Model.CustomerModel();
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("D.N.I.");
        
        customerTable.setModel(model);
        
        try {
            String sql = "SELECT * FROM customer WHERE customer.firstName like concat('%',?,'%')";
            PreparedStatement ps = connection.connection().prepareStatement(sql);
            ps.setString(1, customerName.getText());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                customer.setIdCustomer(rs.getInt("idCustomer"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setDNI(rs.getString("dni"));
                
                model.addRow(new Object[]{
                    customer.getIdCustomer(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getDNI()
                });
            }
            
           customerTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al mostrar cliente" + e.toString());
        } finally {
            connection.disconnect();
        }
        
        for(int column = 0; column < customerTable.getColumnCount(); column++){
            Class<?> columnClass = customerTable.getColumnClass(column);
            customerTable.setDefaultEditor(columnClass, null);
        }
        
    }
    
        public void selectCustomer(JTable customerTable, JTextField id, JTextField firstName, JTextField lastName, JTextField dni){
     
            int row = customerTable.getSelectedRow();

            try {

                if(row >= 0){
                    id.setText(customerTable.getValueAt(row, 0).toString());
                    firstName.setText(customerTable.getValueAt(row, 1).toString());
                    lastName.setText(customerTable.getValueAt(row, 2).toString());
                    dni.setText(customerTable.getValueAt(row, 3).toString());
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
            }
        }
        
        public void showLastInvoice(JTable invoiceTable, JTextField productId, JTextField productName, JTextField productPrice, JTextField quantity, JTextField stock) {
            DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
            int stockAvailable = Integer.parseInt(stock.getText());
            String productInvoiceId = productId.getText();
            
            for(int i = 0; i < model.getRowCount(); i++){
                String id = (String) model.getValueAt(i, 0);
                if(id.equals(productInvoiceId)){
                    JOptionPane.showMessageDialog(null, "El productp ya se encuentra registrado.");
                    return;
                }
            }
            
            String productInvoiceName = productName.getText();
            double productInvoicePrice = Double.parseDouble(productPrice.getText());
            int quantityProduct = Integer.parseInt(quantity.getText());
            
            if(quantityProduct > stockAvailable) {
                JOptionPane.showConfirmDialog(null, "La cantidad de venta no puede ser mayo al stock disponible");
                return;
            }
            
            double subTotal = productInvoicePrice * quantityProduct;
            model.addRow(new Object[]{
                productId,
                productInvoiceName,
                productInvoicePrice,
                quantity.getText(),
                subTotal
            });
            
            
        }
        
    
}
