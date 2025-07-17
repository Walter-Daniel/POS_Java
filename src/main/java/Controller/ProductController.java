package Controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductController {
    
        public void showProducts(JTable tbTotalProducts){
            
        Config.CConnection connection = new Config.CConnection();
        Model.ProductModel product = new Model.ProductModel();
        DefaultTableModel model = new DefaultTableModel();
        
        String sql = "";
        
        model.addColumn("idProduct");
        model.addColumn("productName");
        model.addColumn("productPrice");
        model.addColumn("Stock");
        
        tbTotalProducts.setModel(model);
        sql = "Select product.idProduct, product.productName, product.productPrice, product.stock from product";
    
        try { 
            Statement st = connection.connection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
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
            tbTotalProducts.setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar productos: " +  e.toString());
        }finally{
            connection.disconnect();
        }
    
    }
    
    
    // ADD PRODUCT
    
    public void addProduct(JTextField productName, JTextField productPrice, JTextField stock) {
        Config.CConnection connection = new Config.CConnection();
        Model.ProductModel product = new Model.ProductModel();
        
        String sql = "INSERT INTO product (productName, productPrice, stock) VALUES (?,?,?)"; 

        try {
            product.setName(productName.getText());
            product.setPrice(Double.valueOf(productPrice.getText()));
            product.setStock(Integer.parseInt(stock.getText()));
            
            CallableStatement cs = connection.connection().prepareCall(sql);
            cs.setString(1, product.getName());
            cs.setDouble(2, product.getPrice());
            cs.setInt(3, product.getStock());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar un nuevo producto: " + e.toString());
        } finally {
            connection.disconnect();
        }
    }
    
}
