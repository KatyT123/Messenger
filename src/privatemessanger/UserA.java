
package privatemessanger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


/**
 *
 * @author Tsagkaraki Aikaterini
 */

public class UserA extends RegularUser{
    
    private static Connection connection;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    public static Scanner sc = new Scanner(System.in);
    
    public static void ShowMessagesTable(){
 
         System.out.println("To see entire message Table press 1. \n To see a message with specific id press 2. \n   For exit press 0. \n");
         Scanner sc = new Scanner (System.in);
         int input = sc.nextInt();  
         if(input == 1) ShowEntireTableMessage();
         if(input == 2) ShowSpecificMessage(); 
    }
    
    public static void ShowEntireTableMessage() {
           try{ 
               connection = DataBaseAccess.DataBaseConnection();
               String query = "SELECT id,to_user_id,from_user_id,time_sent,subject,text,opened  FROM message"; 
               pst = connection.prepareStatement(query);
               rs = pst.executeQuery();
            
               while (rs.next()){
                    System.out.println("id: " + rs.getInt(1) + " to:  " + rs.getString(2) + " from:  " + rs.getString(3) + 
                       "  sended on: " + rs.getString(4) + "   subject: " + rs.getString(5) + "   message: " + rs.getString(6) + "read status: " + rs.getInt(7));
               }
               rs.close();
               pst.close();
            }catch (SQLException ex){
                System.out.println(ex.toString());
                System.exit(0);
            }
           connection = DataBaseAccess.CloseConnection(connection);

    }
    
    public static void ShowSpecificMessage() {
        System.out.println ("Type the specific messages id numbers separated with comma : ");
        Scanner sc = new Scanner(System.in);
        String input1 = sc.nextLine();
        String inputArray [] = input1.split(",");
        connection = DataBaseAccess.DataBaseConnection();    
        for(String input2 : inputArray){
        
        try{
             String query = "SELECT id,to_user_id,from_user_id,time_sent,subject,text,opened FROM message WHERE id = ?";
             pst = connection.prepareStatement(query);
             pst.setInt(1,Integer.parseInt(input2));
             rs = pst.executeQuery();
             
             while (rs.next()){
                 System.out.println("message id: " + rs.getInt(1) + "   to:  " + rs.getString(2) + "   from:  " + rs.getString(3) + 
                    "   send on: " + rs.getString(4) + "   subject: " + rs.getString(5) + "   message: " + rs.getString(6) + "   read status: " + rs.getInt(7));
             }
             rs.close();
             pst.close();
        }catch (SQLException ex){
             System.out.println(ex.toString());
             System.exit(0);
        }
        }
        connection = DataBaseAccess.CloseConnection(connection);
}
    
    
    
    
}
    




