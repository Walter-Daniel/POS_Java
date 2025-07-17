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
    
    // select customer
    
    public void selectCustomer(JTable totalCustomer, JTextField id, JTextField firstName, JTextField lastName, JTextField dni){
        int row = totalCustomer.getSelectedRow();
        try{
            if(row >= 0){
                id.setText(totalCustomer.getValueAt(row, 0).toString());
                firstName.setText(totalCustomer.getValueAt(row, 1).toString());
                lastName.setText(totalCustomer.getValueAt(row, 2).toString());
                dni.setText(totalCustomer.getValueAt(row, 3).toString());

            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
        }
    }
    
    // update customer
    
    public void updateCustomer(JTextField id, JTextField firstName, JTextField lastName, JTextField dni){
        Config.CConnection connection = new Config.CConnection();
        Model.CustomerModel customer = new Model.CustomerModel();
        String sql = "UPDATE customer SET customer.firstName=?, customer.lastName=?, customer.dni=? WHERE customer.idCustomer=?";
        
        try {
            customer.setIdCustomer(Integer.parseInt(id.getText()));
            customer.setFirstName(firstName.getText());
            customer.setLastName(lastName.getText());
            customer.setDNI(dni.getText());
            
            CallableStatement cs = connection.connection().prepareCall(sql);
            cs.setString(1, customer.getFirstName());
            cs.setString(2, customer.getLastName());
            cs.setString(3, customer.getDNI());
            cs.setInt(4, customer.getIdCustomer());

            cs.execute();
            JOptionPane.showMessageDialog(null, "El cliente se ha modificado con éxito!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + e.toString());
        } finally {
            connection.disconnect();
        }
    }
    
    // CLEAN FIELDS
    
    public void cleanFields(JTextField id, JTextField firstName, JTextField lastName, JTextField dni) {
        
        id.setText("");
        firstName.setText("");
        lastName.setText("");
        dni.setText("");
        
    }
    
}
