/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DUC
 */
public class DBconnect {
    public static  Connection getConnection() throws ClassNotFoundException{
        Connection conn=null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=DriverManager.getConnection("jdbc:sqlserver://LAPTOP-ERMID7T6;database=chatandanh;encrypt=false;trustServerCertificate=true;user=minhduc08022004;password=Duc08022004");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(getConnection());
    }
}
