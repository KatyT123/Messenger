
package privatemessanger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Tsagkaraki Aikaterini
 */
public class FilesAccess {
    private static String to_user_id = null;
    private static String from_user_id = null;
    private static String username = null;
    

public static void WriteInFile(int id, String to_user_id, String from_user_id, String date_time, String subject, String text){
        
    PrintWriter out = null;
        
    try {
	 out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\USERS\\KATY\\Desktop\\"+ from_user_id + ".txt", true)));		  
	 out.println("message id: " + id + "  from:  " + from_user_id + "  to:  " + to_user_id +"  send on:  " + date_time + " \n  subject: "
                   + subject + "  message:  " + text );
           
    } catch ( IOException ex ) {
            System.err.println(ex.getMessage());
	    	  
    } finally{
	    if(out != null){
	    out.close();
	    }
		    
    }
}
    
   
    
    
    
}
