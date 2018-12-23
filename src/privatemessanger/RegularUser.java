
package privatemessanger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import static privatemessanger.DataBaseAccess.sc;

/**
 *
 * @author Tsagkaraki Aikaterini
 */
public class RegularUser {
    
    private static Connection connection;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    public static Scanner sc = new Scanner(System.in);
  

public static void WriteMessage(String username) {
        
        String recipient = null;
        String subject = null;
        String message = null;
        String time = null;
        String str = null;
        Boolean opened = false;
        
        try {
            connection = DataBaseAccess.DataBaseConnection();
            System.out.println("Please give a recipient for your message ");
            recipient = sc.nextLine();
            //Check if recipient exist in db, write a message in DB, append message in file
            if(CheckIfRecipientExistInDB(recipient,connection)){
                 System.out.println("Please write the message's subject below :");
                 subject = sc.nextLine();
                 message = GetMessageText();
                 time  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                 
                  str = new StringBuilder()
                 .append("INSERT message (to_user_id,from_user_id,time_sent,subject,text,opened)")
                 .append("VALUES ( ?, ?, ?, ?, ?, ?);").toString();
           
                 try (PreparedStatement statement = connection.prepareStatement(str)) {
			statement.setString(1, recipient);
                        statement.setString(2, username);
                        statement.setString(3, time);
                        statement.setString(4, subject);
                        statement.setString(5, message);
                        statement.setBoolean(6, opened);
                        
			int rowsAffected = statement.executeUpdate();
			System.out.println(rowsAffected + " row(s) inserted");
                        FilesAccess.WriteInFile(BringSpecificMessageId(recipient,username,time,connection), recipient, username, time, subject, message);
                }
                  rs.close();
                  pst.close();
            }
            else 
                System.out.println("recipient's username is not found in DB");
             
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
        } DataBaseAccess.CloseConnection(connection);
    }

public static void ReadUnreadedMessages(String username) {
    int val = 1;
    try {
         connection = DataBaseAccess.DataBaseConnection();
         String query = "SELECT from_user_id, time_sent, subject, text, opened FROM message WHERE to_user_id = ?";  
         pst = connection.prepareStatement(query);
         pst.setString(1, username);
         rs = pst.executeQuery();
         
         while (rs.next()){
            val = rs.getInt("opened"); 
            if (val == 0) {
               System.out.println(" form: " + rs.getString(1) + " " + rs.getString(2) + "\n subject: " + rs.getString(3) + "\n " + rs.getString(4)+"\n");
               
            }
         }
         query = "UPDATE message SET opened = 1 WHERE to_user_id = ?";
         pst = connection.prepareStatement(query);
         pst.setString(1, username);
	 pst.executeUpdate();
         rs.close();
         pst.close();
   
   }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
  } 
    DataBaseAccess.CloseConnection(connection);
       
 }

public static void ModifyMyMessages (String username){
    System.out.println(" To see all messages you have recieved press 1. \n To see all messages you have sended press 2. "
            + "\n To edit a message you've sended press 3. \n For EXIT press 0. \n");
    Scanner sc = new Scanner(System.in);
    int action = sc.nextInt();
     
    while(action != 0){
        
       switch (action) {  
            case 1:  ReadRecievedMessages(username);
                     break;
            case 2:  ReadMyMessages(username);
                     break;
            case 3:  EditMyMessage(username);
                     break;    
               }  
       System.out.println(" To see all messages you have recieved press 1. \n To see all messages you have sended press 2. "
            + "\n To edit a message you've sended press 3. \n For EXIT press 0. \n ");
       action = sc.nextInt();
    }
}

public static void ReadRecievedMessages(String username) {
     try {
         connection = DataBaseAccess.DataBaseConnection();
         String query = "SELECT from_user_id, time_sent, subject, text, opened FROM message WHERE to_user_id = ?";  
         pst = connection.prepareStatement(query);
         pst.setString(1, username);
         rs = pst.executeQuery(query);
         
         while (rs.next()){
            System.out.println(" form: " + rs.getString(1) + " " + rs.getString(2) + "\n subject: " + rs.getString(3) + "\n " + rs.getString(4)+"\n");
         }
         rs.close();
         pst.close();
   
   }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
  } 
   connection = DataBaseAccess.CloseConnection(connection);      
    }

public static void ReadMyMessages(String username) {
      try {
         connection = DataBaseAccess.DataBaseConnection();
         String query = "SELECT id, from_user_id,to_user_id, time_sent, subject, text, opened FROM message WHERE from_user_id = ?";  
         pst = connection.prepareStatement(query);
         pst.setString(1, username);
         rs = pst.executeQuery();
         
         while (rs.next()){
            System.out.println("id: "+rs.getInt(1)+ "  form: " + rs.getString(2) + "  to: " + rs.getString(3) + "  " + rs.getString(4)+ "\n subject: " + rs.getString(5) + "\n " + rs.getString(6)+"\n");
         }
         rs.close();
         pst.close();
   
   }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
  } 
   connection = DataBaseAccess.CloseConnection(connection);       
}

public static void EditMyMessage(String username) {
     System.out.println ("Type the id number of sended from you message to edit: ");
     Scanner sc = new Scanner(System.in);
     int id = sc.nextInt();
     connection = DataBaseAccess.DataBaseConnection();
     if(GetExistingMessageIdToEditOfSendedFromMe(id,username,connection)){   
         
        //  text edit performance
         try{
            System.out.println("Please type the edited text here : ");
            String message = GetMessageText();
            String query1 = "UPDATE message SET text = ? WHERE id = ? AND from_user_id = ?";
            PreparedStatement pst1 = connection.prepareStatement(query1);
            pst1.setString(1, message);
            pst1.setInt(2, id);
            pst1.setString(3, username);
            pst1.executeUpdate();
            System.out.println("Text updated !");
            rs.close();
            pst.close();
            pst1.close();
         
         }catch (SQLException ex){
             System.out.println(ex.toString());
             System.exit(0);
         }
          connection = DataBaseAccess.CloseConnection(connection);
     }
}   

private static int BringSpecificMessageId(String recipient, String username, String time, Connection connection){
    int id = 0;
     try {
            String query = "SELECT id FROM message WHERE from_user_id = ? AND to_user_id = ? AND time_sent =?";
            pst = connection.prepareStatement(query);
            pst.setString(1,username);
            pst.setString(2,recipient);
            pst.setString(3, time);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
     }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
        } 
    return id;
}

private static boolean CheckIfRecipientExistInDB(String recipient, Connection connection){
     boolean flag = false;
     String query = "SELECT * FROM USER WHERE user_id = ?";
     
     try{
         pst = connection.prepareStatement(query);
         pst.setString(1,recipient);
         rs = pst.executeQuery();
         if (rs.next()) {
             System.out.println("recipient's username found!  ");
             flag = true;
         }
         pst.close();
         rs.close();
     }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
     } 
     return flag;
}

public static String GetMessageText() {
    Scanner sc = new Scanner(System.in);      
    int act = 1;
    String message = null;
   
    while(act == 1){
         System.out.println("Please write your message below. It should be less or equal to 250 characters :");
         message = sc.nextLine();
         act = 0;
         if (message.length()>250){
              System.out.println("You exceed the limit of 250 characters !");
              message = message.substring(0,250);
              System.out.println(" Your text has been cut off like this : \n" + message + 
                    "\n If you want to type a new text press 1. Else press ZERO \n");
              Scanner sc1 = new Scanner(System.in);
              act = sc1.nextInt();
         }   
    }
    return message;
   }

public static boolean GetExistingMessageIdToEditOfSendedFromMe(int id, String username, Connection connection) {
     boolean flag = false;
     try {
            String query = "SELECT id,to_user_id,from_user_id,time_sent,subject,text,opened FROM message WHERE id = ? AND from_user_id = ?";
            pst = connection.prepareStatement(query);
            pst.setInt(1,id);
            pst.setString(2, username);
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("The message id found!");
                id = rs.getInt(1);
                System.out.println("message id: " + rs.getInt(1) + "   to:  " + rs.getString(2) + "   from:  " + rs.getString(3) + 
                       "   send on: " + rs.getString(4) + "   subject: " + rs.getString(5) + "   message: " + rs.getString(6) + "   read status: " + rs.getInt(7));
                flag = true;
            }else{
                System.out.println("The message id is not found!");
            }
     }catch (SQLException ex) {
            System.out.println(ex.toString());
            System.exit(0);
        } 
    return flag;
}
}
