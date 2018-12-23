
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
public class AdminUser extends UserC{
    
    private static Connection connection;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    public static Scanner sc = new Scanner(System.in);
    
    
public static void ModifyUserTable(){

         System.out.println("\n To see User Table in DB press 1. \n To insert new user in DB press 2. \n To delete an existing user from DB press 3. "
                 + "\n To update an existing user press 4. \n For EXIT press 0");
         Scanner sc = new Scanner(System.in);
         int action = sc.nextInt(); 
         
          while(action != 0){
        
               switch (action) {  
                     case 1:  WiewUserTable();
                              break;
                     case 2:  InsertUser();
                              break;
                     case 3:  DeleteUser();
                              break;
                     case 4:  UpdateUser();
                              break;
               }  
               
              System.out.println("\n To see User Table in DB press 1. \n To insert new user in DB press 2. \n To delete an existing user from DB press 3. "
                 + "\n To update an existing user press 4. \n For EXIT press 0");
              action = sc.nextInt(); 
          }
}

public static void DeleteUser(){
     
     connection = DataBaseAccess.DataBaseConnection();
     System.out.println("Type the username to delete: ");
     String userToDelete = sc.nextLine();
     String query = "DELETE FROM user WHERE user_id = ?";
     
     try (PreparedStatement pst = connection.prepareStatement(query)) {
          pst.setString(1, userToDelete);
          pst.executeUpdate();
          System.out.println("User registration deleted from DB !");
          pst.close();
     }catch (Exception e){} 
      connection = DataBaseAccess.CloseConnection(connection);
}

public static void InsertUser(){
    
    connection = DataBaseAccess.DataBaseConnection();
    Scanner sc = new Scanner(System.in);
    System.out.println("Type the username to insert: ");
    String usernameToInsert = sc.nextLine();
    System.out.println("Type the password to insert: "); 
    String psswordToInsert = sc.nextLine();
    System.out.println(" Type user's priority. \n For 'admin' priority type 1. For priority 'C' type 2. For priority 'B' type 3. \n For priority 'A' type 4. For 'Regular' priority type 5 ");
    Scanner sc1 = new Scanner (System.in);
    int priority = sc1.nextInt();
    System.out.print("Inserting a new row into user table...");
    String query = new StringBuilder()
    .append("INSERT user (user_id, user_password, priority_id) ")
    .append("VALUES (?, ?, ?);").toString();
    
    try (PreparedStatement pst = connection.prepareStatement(query)){     
         pst.setString(1, usernameToInsert );
	 pst.setString(2, psswordToInsert);
         pst.setInt(3, priority);
         pst.executeUpdate();
         System.out.println("done");
         pst.close();
    }catch (Exception e){} 
     connection = DataBaseAccess.CloseConnection(connection);
}

private static void UpdateUser() {
    
    connection = DataBaseAccess.DataBaseConnection();
    Scanner sc = new Scanner(System.in);
    System.out.println(" Type the username to update: ");
    String usernameToUpdate = sc.nextLine();
    System.out.println("\n You can update user's priority only. Type below the new priority to update \n "
            + "For 'Admin' Priority press 1. \n For 'C' Priority press 2. \n For 'B' Priority press 3. \n For 'A' Priority press 4. "
            + "\n For 'Regular' User Priority press 5. "); 
    Scanner sc1 = new Scanner (System.in);
    int priorityToUpdate = sc1.nextInt();
    System.out.print("Updating a row into user table...");
    String query = "UPDATE user SET priority_id = ? WHERE user_id = ?";
    try{
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1,priorityToUpdate);
        pst.setString(2, usernameToUpdate);
        pst.executeUpdate();
        System.out.println("Row updated !");
        pst.close();
    }catch (Exception e){} 
     connection = DataBaseAccess.CloseConnection(connection);
}

    private static void WiewUserTable() {
        
        connection = DataBaseAccess.DataBaseConnection();
        System.out.println("The existing user table in DB is shown below \n");
        String query = "SELECT user_id,user_password,user.priority_id,priority.user_priority FROM user,priority WHERE user.priority_id = priority.priority_id; ";
        try{
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
          
            while (rs.next()){
                 System.out.println("username: " + rs.getString(1) + "  password: " + rs.getString(2) + "  user's priority: " + rs.getInt(3) + "  type: " + rs.getString(4));
            }
            pst.close();
            rs.close();
       }catch (Exception e){} 
       connection = DataBaseAccess.CloseConnection(connection);
  }
}
