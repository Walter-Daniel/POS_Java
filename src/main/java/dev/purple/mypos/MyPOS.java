package dev.purple.mypos;

public class MyPOS {

    public static void main(String[] args) {
        /*Config.CConnection newConnection = new Config.CConnection();
        newConnection.connection();*/
        
        View.PrincipalMenu principalMenu = new View.PrincipalMenu();
        principalMenu.setVisible(true);
    }
}
