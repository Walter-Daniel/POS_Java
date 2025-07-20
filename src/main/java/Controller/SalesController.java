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
    
}
