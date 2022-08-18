import java.io.*;
import java.util.*;

public class Assembler {

    public String instructions = "";
    BufferedReader br;

    public void file_handler() throws FileNotFoundException, IOException {   //File handler (accesess & reads file)
        
        while(true) {
            
            Scanner sc = new Scanner(System.in);
            String in = sc.nextLine() + ".asm";   
            File f = new File(in);                                   //Creates instance of the file taken from input
                
            if(f.exists()) { 
                
                System.out.println("File : " + in);
                br = new BufferedReader(new FileReader(f));
                String s;
            
                while((s = br.readLine()) != null)                         //adds lines read from the file to a string
                    instructions += (s + "\n");                             //This string stores all the data of the file

                System.out.println("File read successfully.");
                System.out.println("***********************");

                sc.close();
                br.close();
                break;

            } else {                                                 //prints error for any faults in file input
                  
                  System.out.println("ERROR : FILE INVALID");
                  System.out.println("***********************");

              }
        }  
    }

    public void cleaner() throws IOException {
		
        String i, temp = "";
        br = new BufferedReader(new StringReader(instructions));

        

    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Assembler asmb = new Assembler();

        asmb.file_handler();
        

    }
    
}
