package Controller;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios: " +  e.getMessage());
        }finally{
            connection.disconnect();
        }
    
    }
}
