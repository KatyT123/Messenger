
package privatemessanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Tsagkaraki Aikaterini
 */
public class UserB extends UserA {
   
    private static Connection connection;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    public static Scanner sc = new Scanner(System.in);
    

private static boolean GetExistingMessageIdToEdit(int id,Connection connection) {
         boolean flag = false;
     try {
            String query = "SELECT id,to_user_id,from_user_id,time_sent,subject,text,opened FROM message WHERE id = ?";
            pst = connection.prepareStatement(query);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("The message ID is found!");
                id = rs.getInt(1);
                System.out.println("message id: " + rs.getInt(1) + "   to:  " + rs.getString(2) + "   from:  " + rs.getString(3) + 
                       "   send on: " + rs.getString(4) + "   subject: " + rs.getString(5) + "   message: " + rs.getString(6) + "   read status: " + rs.getInt(7));
                flag = true;
            }else{
                System.out.println("The message ID is not found!");
            }
            pst.close();
            rs.close();
     }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
        } 
    return flag;
    }
    
  
public void ModifyMessageTable(){
    System.out.println(" Optional Menu to Modify Message Table in DB : \n Press 1 to Read specific messages. \n Press 2 to Read entire Message Table. \n "
            + "Press 3 to Edit specific mesagge. \n Press 0 for EXIT");
    int action = sc.nextInt();
    
    while(action != 0){
          switch (action) {  
                case 1:  ShowSpecificMessage();
                         break;
                case 2:  ShowEntireTableMessage();
                         break;
                case 3:  EditTableMessage();
                         break;
          }  
          
           System.out.println(" Optional Menu to Modify Message Table in DB : \n Press 1 to Read specific messages. \n Press 2 to Read entire Message Table. \n "
            + "Press 3 to Edit specific mesagge. \n Press 0 for EXIT");
          action = sc.nextInt();
        }
}
public static void EditTableMessage(){
     
    System.out.println ("Type the id number of  message to edit: ");
    Scanner sc = new Scanner(System.in);
    int id = sc.nextInt();
    connection = DataBaseAccess.DataBaseConnection();
    if(GetExistingMessageIdToEdit(id,connection)){
  
      //  text edit performance
       try{ 
             String message = GetMessageText();
             String query1 = "UPDATE message SET text = ? WHERE id = ?";
             pst = connection.prepareStatement(query1);
             pst.setString(1,message);
             pst.setInt(2, id);
             pst.executeUpdate();
             System.out.println("Text updated !");
             pst.close();
              
             }catch (SQLException ex){
                 System.out.println(ex.toString());
                 System.exit(0);
             }
        connection = DataBaseAccess.CloseConnection(connection);
    }
   }
 } 

