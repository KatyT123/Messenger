
package privatemessanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author KATY
 */
public class LoginForm {
    
      private static Scanner sc = new Scanner(System.in);
      private static Connection connection = null;
      private static PreparedStatement pst = null;
      private static ResultSet rs = null;
      public static String username = null;
      public static String password = null;
      public static int priority;
      
public static boolean CheckLogIn(String username, String password){ 
       boolean found = false;
       
       try{
            String sql = "select * from user where user_id=? and user_password=?";
            connection = DataBaseAccess.DataBaseConnection();
            pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
           
            if(rs.next()){
                System.out.println("username and password matched");
                System.out.println("Wellcome user : " + username);
                found = true;
            }
            else {
                System.out.println("username and password do not matched");
                System.exit(1);
            }
            }catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        }
        connection = DataBaseAccess.CloseConnection(connection);
        return found;
    } 

public static int UserPriority(String username){
   
   try {
         connection = DataBaseAccess.DataBaseConnection();
         //String query = "SELECT priority_id FROM user WHERE user_id = ?"; 
         
         String query = "SELECT priority.user_priority, user.priority_id FROM priority, user WHERE priority.priority_id = user.priority_id AND user_id = ?"; 
         
         pst = connection.prepareStatement(query);
         pst.setString(1, username);
         rs = pst.executeQuery();
         
         while (rs.next()){
            priority = rs.getInt("priority_id");
            System.out.println("Your priority is type: " + rs.getString("user_priority"));
         }
         
         rs.close();
         pst.close();
         
   }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
   }
     connection = DataBaseAccess.CloseConnection(connection);
     return priority;
}

}

