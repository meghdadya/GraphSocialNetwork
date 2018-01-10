package database;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
 
/**
 *
 * @author babe
 */
public class DBConnection {
     /**
     * Connect to a sample GSNetwork
     */
	 private Connection connect() {
            // db parameters
        		URL sqlScriptUrl = DBConnection.class
                    .getClassLoader().getResource("sql/GSNetwork.db");
            String url = "jdbc:sqlite:"+sqlScriptUrl;
            // create a connection to the database
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
    }
  
    /**
     * Insert a new row into the users table
     *
     * @param name
     * @param email
     * @param bio
     * @param photo
     */
    public void insert_users(String name, String email,String password,String bio,String photo) {
        String sql = "INSERT INTO tbl_users(name,email,password,bio,photo) VALUES('"+name+"','"+email+"','"+password+"','"+bio+"','"+photo+"')";
        try (Connection conn = this.connect();
        		Statement stmt = conn.createStatement()) {
    		stmt.execute(sql);
    		stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String select_users() {
        try (Connection conn = this.connect();
        		Statement stmt = conn.createStatement()) {
        		ResultSet rs = stmt.executeQuery( "SELECT * FROM tbl_users;" );
        	      while ( rs.next() ) {
        	         int id = rs.getInt("id");
        	         String  name = rs.getString("name");
        	         String age  = rs.getString("email");
        	         System.out.println( "ID = " + id );
        	         System.out.println( "NAME = " + name );
        	         System.out.println( "AGE = " + age );
        	         System.out.println();
        	      }
        	      rs.close();
        	      stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return "";
    }
    
    
//    public static void main(String[] args) {
//    new 	DBConnection().select_users();
//    }
    
}
