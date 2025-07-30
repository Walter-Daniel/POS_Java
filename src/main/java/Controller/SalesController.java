package Controller;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
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
        
        public void showInvoice(JTable invoiceTable, JTextField productId, JTextField productName, JTextField productPrice, JTextField quantity, JTextField stock) {
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
                JOptionPane.showMessageDialog(null, "La cantidad de venta no puede ser mayo al stock disponible");
                return;
            }
            
            double subTotal = productInvoicePrice * quantityProduct;
            model.addRow(new Object[]{
                productInvoiceId,
                productInvoiceName,
                productInvoicePrice,
                quantity.getText(),
                subTotal
            });
        }
        
        public void deleteProductInInvoice(JTable invoiceTable){
            
            DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
            int indexSelected = invoiceTable.getSelectedRow();
            
            try {
                 if(indexSelected != -1){
                model.removeRow(indexSelected);
                }else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e);
            }
            
            
        }
        
       public void calculateTotalAmount(JTable invoiceTable, JLabel ivaLabel, JLabel totalAmountLabel) {
            DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();

            double subTotal = 0.0;
            double ivaRate = 0.18;

            // Sumar subtotales 
            for (int i = 0; i < model.getRowCount(); i++) {
                Object value = model.getValueAt(i, 4);
                if (value instanceof Number) {
                    subTotal += ((Number) value).doubleValue();
                } else {
                    try {
                        subTotal += Double.parseDouble(value.toString());
                    } catch (NumberFormatException e) {
                        System.err.println("Valor inválido en fila " + i + ": " + value);
                    }
                }
            }

            double ivaAmount = subTotal * ivaRate;
            double total = subTotal + ivaAmount;

            // Formatear para mostrar
            DecimalFormat formatter = new DecimalFormat("#.##");

            totalAmountLabel.setText(formatter.format(total));
            ivaLabel.setText(formatter.format(ivaAmount));
        }

        public void createInvoice(JTextField idCustomer){
            Config.CConnection connection = new Config.CConnection();
            Model.CustomerModel customer = new Model.CustomerModel();
            
            String sql = "INSERT INTO invoice (invoiceDate, idCustomer) VALUES (curdate(),?);";
            
            try {
                customer.setIdCustomer(Integer.parseInt(idCustomer.getText()));
                CallableStatement cs = connection.connection().prepareCall(sql);
                cs.setInt(1, customer.getIdCustomer());
                cs.execute();
                
                JOptionPane.showMessageDialog(null, "Factura creada");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al intentar crear la factura" + e.toString());
            } finally {
                connection.disconnect();
            }
        }
    
        public void makeSale(JTable invoiceTable){
              Config.CConnection connection = new Config.CConnection();
              
              String sql = "INSERT INTO invoiceDetail (idInvoice, idProduct, quantity, price) VALUES ((SELECT MAX(idInvoice) FROM invoice),?,?,?);";
              String updateStockSQL = "UPDATE product SET product.stock = stock - ? WHERE idProduct = ?;";
              
              
              try {
                  
                  PreparedStatement psDetail = connection.connection().prepareStatement(sql);
                  PreparedStatement psStock = connection.connection().prepareStatement(updateStockSQL);

                  int row = invoiceTable.getRowCount();
                  
                  for(int i = 0; i<row; i++){
                      int productId = Integer.parseInt(invoiceTable.getValueAt(i, 0).toString());
                      int quantity = Integer.parseInt(invoiceTable.getValueAt(i, 3).toString());
                      double totalPrice = Double.parseDouble(invoiceTable.getValueAt(i, 2).toString());
                      
                      psDetail.setInt(1, productId);
                      psDetail.setInt(2, quantity);
                      psDetail.setDouble(3, totalPrice);
                      psDetail.executeUpdate();
                      
                      psStock.setInt(1, quantity);
                      psStock.setInt(2, productId);
                      psStock.executeUpdate();
                  }
                  
                  JOptionPane.showMessageDialog(null, "Se realizó con éxito la venta");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al realizar la venta" + e.toString());
            } finally {
                  connection.disconnect();
            }
        }
        
        public void cleanFields(JTextField findCustomer, JTable customerTable,JTextField findProduct, JTable productTable,
                JTextField selectIdCustomer, JTextField selectCustomerFirstName, JTextField selectCustomerLastName, 
                JTextField selectCustomerDni, JTextField selectProductId, JTextField selectProductName, JTextField selectProductPrice,
                JTextField selectStock, JTextField selectTotalPrice,JTextField selectQuantity, JTable invoice, JLabel IVA, JLabel total){
            
                findCustomer.setText("");
                findCustomer.requestFocus();
                DefaultTableModel customerModel = (DefaultTableModel) customerTable.getModel();
                customerModel.setRowCount(0);

                findProduct.setText("");
                DefaultTableModel productModel = (DefaultTableModel) productTable.getModel();
                productModel.setRowCount(0);

                selectIdCustomer.setText("");
                selectCustomerFirstName.setText("");
                selectCustomerLastName.setText("");
                selectCustomerDni.setText("");

                selectProductId.setText("");
                selectProductName.setText("");
                selectProductPrice.setText("");
                selectStock.setText("");
                selectTotalPrice.setText("");

                total.setText("");
                total.setEnabled(false);

                selectQuantity.setText("");
                
                DefaultTableModel invoiceModel = (DefaultTableModel) productTable.getModel();
                invoiceModel.setRowCount(0);
                
                IVA.setText("");
                total.setText("");
            
        } 
        
        public void showLastInvoiceNumber(JLabel lastInvoice) {
            Config.CConnection connection = new Config.CConnection();
            
            try {
                String sql = "SELECT MAX(idInvoice) as lastInvoice FROM invoice";
                PreparedStatement ps = connection.connection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    lastInvoice.setText(String.valueOf(rs.getInt("lastInvoice")));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al mostrar el número de factura creada" + e.toString());
            } finally {
                connection.disconnect();
            }
        }
}
