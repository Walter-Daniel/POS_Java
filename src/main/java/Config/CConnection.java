package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class CConnection {
    
    Connection connect = null;
    String user = "root";
    String password = "admin";
    String db = "dbpos";
    String port = "3306";
    String servidor = "localhost";

    String chain = "jdbc:mysql://"+servidor+":"+port+"/"+db;
    
    public Connection connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(chain, user, password);
            //JOptionPane.showMessageDialog(null, "Conexión correcta a DB");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error de conección a DB:" + e.toString());
        }
        return connect;
    }
    
    public void disconnect(){
        try {
            if(connect != null && !connect.isClosed()){
                connect.close();
                //JOptionPane.showMessageDialog(null, "Conexión cerrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar conexión:" + e.toString());
        }
    }
}
