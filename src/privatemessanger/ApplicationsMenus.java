
package privatemessanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author KATY
 */

public class ApplicationsMenus {
    private static String username = null;
    static Scanner sc = new Scanner(System.in);
    private static int action;
    
    public static void AllUsersMenus(String username){
        int priority = LoginForm.UserPriority(username);
        if (priority == 1){
            AdminPriorityMenu(username);
        }
        if (priority == 2){
            C_PriorityMenu(username);
        }
        if (priority == 3){
            B_PriorityMenu(username);
        }
        if (priority == 4){
            A_PriorityMenu(username);
        }
        if (priority == 5){
            Regular_PriorityMenu(username); 
        }
    }

    private static void AdminPriorityMenu(String username) {
       AdminUser admin = new AdminUser();
       DataBaseAccess.UnreadedMessages(username);
       action = AdminPriorityActionChoise();
       
       while (action != 0){
           
           switch (action) {  
            case 1:  AdminUser.ReadUnreadedMessages(username);
                     break;
            case 2:  AdminUser.WriteMessage(username);
                     break;
            case 3:  AdminUser.ModifyMyMessages(username);
                     break;
            case 4:  AdminUser.ModifyUserTable();
                     break;
            case 5:  admin.ModifyMessageTable();
                     break;
          
          }   
           action = AdminPriorityActionChoise();
       }
       if (action == 0) System.exit(1);
       
    }
  
    private static int AdminPriorityActionChoise(){
        System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. \n To modify user table in DB press 4. \n"
               +  " To modify messages table in DB press 5. \n For EXIT press 0. \n");
        Scanner sc = new Scanner(System.in);
        action = sc.nextInt();
       
       while (action <0 || action >6){
           System.out.println("Wrong action choise !");
            System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. \n To modify user table in DB press 4. \n"
               +  " To modify messages table in DB press 5. \n For EXIT press 0. \n");
           action = sc.nextInt();
       
       }
       return action;
    }

    private static void C_PriorityMenu(String username) {
       UserC c = new UserC();   
       DataBaseAccess.UnreadedMessages(username);
       int action = C_PriorityActionChoise();
          
       while (action != 0){
           
           switch (action) {  
            case 1:  UserC.ReadUnreadedMessages(username);
                     break;
            case 2:  UserC.WriteMessage(username);
                     break;
            case 3:  UserC.ModifyMyMessages(username);
                     break;
            case 4:  c.ModifyMessageTable();
                     break; 
          }   
          action = C_PriorityActionChoise();
    }
       if (action == 0) System.exit(1);
    }

    private static int C_PriorityActionChoise() {
        int action;
        System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. \n "
                + "To modify message table in DB press 4. \n For EXIT press 0. \n");
        Scanner sc = new Scanner(System.in);
        action = sc.nextInt();
       
       while (action <0 || action >4){
           System.out.println("Wrong action choise !");
           System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. \n "
                + "To modify message table in DB press 4. \n For EXIT press 0. \n");
           action = sc.nextInt();
       
       }
        return action;
    }
    
    private static void B_PriorityMenu(String username) {
        UserB b = new UserB();
        DataBaseAccess.UnreadedMessages(username);
        int action = B_PriorityActionChoise();
       
       while (action != 0){
           
           switch (action) {  
            case 1:  UserB.ReadUnreadedMessages(username);
                     break;
            case 2:  UserB.WriteMessage(username);
                     break;
            case 3:  UserB.ModifyMyMessages(username);
                     break;
            case 4:  b.ModifyMessageTable();
                     break;   
          }   
           action = B_PriorityActionChoise();
       }
       if (action == 0) System.exit(1);
    }

    private static int B_PriorityActionChoise() {
        int action;
        System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. "
                + "\n To modify messages table press 4. \n For EXIT press 0. \n");
        Scanner sc = new Scanner(System.in);
        action = sc.nextInt();
       
       while (action <0 || action >4){
           System.out.println("Wrong action choise !");
          System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. "
                + "\n To modify messages table press 4. \n For EXIT press 0. \n");
           action = sc.nextInt();
       
       }
        return action;
    }

    private static void A_PriorityMenu(String username) {
        DataBaseAccess.UnreadedMessages(username);
        int action = A_PriorityActionChoise();
       
       while (action != 0){
           
           switch (action) {  
            case 1:  UserA.ReadUnreadedMessages(username);
                     break;
            case 2:  UserA.WriteMessage(username);
                     break;
            case 3:  UserA.ModifyMyMessages(username);
                     break;
            case 4:  UserA.ShowMessagesTable();
                     break; 
           }
           action = A_PriorityActionChoise();
       }
       if (action == 0) System.exit(1);
    }
    
    private static int A_PriorityActionChoise() {
        int action;
        System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. "
                + "\n To see message table press 4 \n For EXIT press 0. \n");
        Scanner sc = new Scanner(System.in);
        action = sc.nextInt();
       
       while (action <0 || action >4){
           System.out.println("Wrong action choise !");
            System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3. "
                + "\n To see message table press 4 \n For EXIT press 0. \n");
           action = sc.nextInt();
       
       }
        return action;
    }

    private static void Regular_PriorityMenu(String username) {
        DataBaseAccess.UnreadedMessages(username);
        int action = RegularPriorityActionChoise();
       
       while (action != 0){
           
           switch (action) {  
            case 1:  RegularUser.ReadUnreadedMessages(username);
                     break;
            case 2:  RegularUser.WriteMessage(username);
                     break;
            case 3:  RegularUser.ModifyMyMessages(username);
                     break;
           }
           action = RegularPriorityActionChoise();
       }
       if (action == 0) System.exit(1);
    }

    private static int RegularPriorityActionChoise() {
        int action = 0;
        System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3."
                + "\n For EXIT press 0. \n");
        Scanner sc = new Scanner(System.in);
        action = sc.nextInt();
       
       while (action <0 || action >3){
           System.out.println("Wrong action choise !");
           System.out.println("\n To open unreaded messages press 1. \n To write a new message press 2. \n To modify my personal messages press 3."
                + "\n For EXIT press 0. \n");
           action = sc.nextInt();
       
       }
        return action;
    }
}
