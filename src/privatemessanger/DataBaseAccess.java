
package privatemessanger;

/**
 *
 * @author Tsagkaraki Aikaterini
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataBaseAccess {
    
    private static Connection connection;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    public static Scanner sc = new Scanner(System.in);
     
    
public static Connection DataBaseConnection(){
        final String DB_URL = "localhost:3306";
        final String FULL_DB_URL = "jdbc:mysql://" + DB_URL +"/message_system?useSSL=false" ;
        final String DB_USER = "newuser1";
        final String DB_PASSWD = "newuser1";
        Connection connection = null;
        
        try{
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
        }catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        }
        return connection;
    }

public static Connection CloseConnection(Connection connection){
    try{
        connection.close();
    }catch (SQLException ex) { }
    return connection;
}
 


public static void UnreadedMessages(String username) {
    int unreaded = 0;
    int val = 1;
    try {
         connection = DataBaseConnection();
         String query = "SELECT opened FROM message WHERE to_user_id = ?";  
         pst = connection.prepareStatement(query);
         pst.setString(1, username);
         rs = pst.executeQuery();
         
         while (rs.next()){
            val = rs.getInt("opened");
            if (val == 0) unreaded++;
         }
         
         System.out.println("You have : " + unreaded + " unreaded messages \n");
         rs.close();
         pst.close();
         
   }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
        
   } 
    connection = CloseConnection(connection);
}

}

