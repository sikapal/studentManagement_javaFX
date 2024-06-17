package gestionEtudiants.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  
    private static Connection con;


    public void getDBConn() {

        synchronized ("") {

            try{
                if (this.getCon() == null || this.getCon().isClosed()) {
                        try{
                                String url = "jdbc:mysql://localhost:3306/gestionetudiant";
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                setCon(DriverManager.getConnection(url, "root", ""));
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }else{}
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public  static Connection getCon() {
        return con;
    }

    public static void setCon(Connection acon) {
       con = acon;
    }
    
    public static void closeCOnnection(){
    try{
    con.close();
    }catch(SQLException e){
    }
    
    }
}
