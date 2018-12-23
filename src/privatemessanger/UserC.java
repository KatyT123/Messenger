
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
public class UserC extends UserB {
    
    private static Connection connection;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    public static Scanner sc = new Scanner(System.in);
    
@Override
public void ModifyMessageTable(){
    System.out.println(" Optional Menu to Modify Message Table in DB : \n Press 1 to Read specific messages. \n Press 2 to Read entire Message Table. \n "
            + "Press 3 to Edit specific mesagge. \n Press 4 to Delete specific messages. \n Press 5 to Delete all messages from Message Table. \n Press 0 for EXIT");
    
    int action = sc.nextInt();
    
    while(action != 0){
        
          switch (action) {  
                case 1:  ShowSpecificMessage();
                         break;
                case 2:  ShowEntireTableMessage();
                         break;
                case 3:  EditTableMessage();
                         break;
                case 4:  DeleteSpecificMessagesFromDB();
                         break;
                case 5:  DeleteAllMessagesFromDB();
                         break;
          }  
          
           System.out.println(" Optional Menu to Modify Message Table in DB : \n Press 1 to Read specific messages. \n Press 2 to Read entire Message Table. \n "
            + "Press 3 to Edit specific mesagge. \n Press 4 to Delete specific messages. \n Press 5 to Delete all messages from Message Table. \n Press 0 for EXIT");
          action = sc.nextInt();
        }
}    
public static void DeleteSpecificMessagesFromDB() {
        
     System.out.println ("Type the specific id numbers of messages to delete separated with comma : ");
     Scanner sc = new Scanner(System.in);
     String input1 = sc.nextLine();
     String inputArray [] = input1.split(",");
     System.out.println("DELETE MESSAGES ... TO CONTINUE PRESS 1. FOR EXIT PRESS 0.");
     Scanner sc1 = new Scanner(System.in);
     int val = sc1.nextInt();
     
     if (val == 1){
         connection = DataBaseAccess.DataBaseConnection();
    
         try{
              for(String input2 : inputArray){    
               
                  String query = "DELETE FROM message WHERE id = ?";
                  pst = connection.prepareStatement(query);
                  pst.setString(1,input2);
                  pst.executeUpdate();
                  
              }
              pst.close();
           
          }catch (SQLException ex){
              System.out.println(ex.toString());
              System.exit(0);
          }   
     connection = DataBaseAccess.CloseConnection(connection);
     System.out.println("Messages deleted from DB !");
     
     } else {
         System.out.println("Process DELETE Canceled!");
         System.exit(1);
     }
}

public static void DeleteAllMessagesFromDB() {
    
     System.out.println ("DELETE ALL MESSAGES FROM DB!. TO CONTINUE PRESS 1. FOR EXIT PRESS 0 ");
     Scanner sc = new Scanner(System.in);
     int input = sc.nextInt();
     
     if (input == 1){
         try{
             
            connection = DataBaseAccess.DataBaseConnection();
            String query = "DELETE FROM message ";
            pst = connection.prepareStatement(query);
            pst.executeUpdate();
            System.out.println("All Messages deleted from DB !");
            pst.close();
            
          }catch(SQLException ex){
              System.out.println(ex.toString());
              System.exit(0);
          }   
         connection = DataBaseAccess.CloseConnection(connection);
      }
      else System.exit(1);
 }

}
