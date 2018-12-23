
package privatemessanger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

/**
 *
 * @author KATY
 */
public class PrivateMessanger {

    public static void main(String[] args) {
       String username = null;
       String password = null;
       Scanner sc = new Scanner(System.in);
       
       System.out.println("Type your username :");
       username = sc.nextLine();
       System.out.println("Type your password :");
       password = sc.nextLine();
       if (LoginForm.CheckLogIn(username, password)) ApplicationsMenus.AllUsersMenus(username);
       
   
    }
    

}
