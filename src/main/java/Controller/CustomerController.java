package Controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CustomerController {
    public void showCustomers(JTable tbTotalCustomers){
        Config.CConnection connection = new Config.CConnection();
        
        Model.CustomerModel customer = new Model.CustomerModel();
        
        DefaultTableModel model = new DefaultTableModel();
        
        String sql = "";
        
        model.addColumn("id");
        model.addColumn("firstName");
        model.addColumn("lastName");
        model.addColumn("dni");
        
        tbTotalCustomers.setModel(model);

        sql = "Select customer.idCustomer, customer.firstName, customer.lastName, customer.dni from customer";
    
        try { 
            Statement st = connection.connection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
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
            tbTotalCustomers.setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios: " +  e.toString());
        }finally{
            connection.disconnect();
        }
    
    }
    
    
    // add customer
    
    public void addCustomer(JTextField firstName, JTextField lastName, JTextField dni) {
        Config.CConnection connection = new Config.CConnection();
        Model.CustomerModel customer = new Model.CustomerModel();
        
        String sql = "insert into customer (firstName, lastName, dni) values (?,?,?)"; 

        try {
            customer.setFirstName(firstName.getText());
            customer.setLastName(lastName.getText());
            customer.setDNI(dni.getText());
            
            CallableStatement cs = connection.connection().prepareCall(sql);
            cs.setString(1, customer.getFirstName());
            cs.setString(2, customer.getLastName());
            cs.setString(3, customer.getDNI());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar un nuevo cliente: " + e.toString());
        } finally {
            connection.disconnect();
        }
    }
}
