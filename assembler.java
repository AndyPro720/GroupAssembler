import java.io.*;
import java.util.*;

public class Assembler {

    public String instructions = "";
    BufferedReader br;

    public void file_handler() throws FileNotFoundException, IOException {   //File handler (accesess & reads file)
        
        while(true) {
            
            Scanner sc = new Scanner(System.in);
            String in = sc.nextLine() + ".asm";

            if(in.exists()) {  
    
                System.out.println("File : " + file);   
                File f = new File(file);                                   //Creates instance of the file taken from input

                br = new BufferedReader(new FileReader(f));
                String s;
            
                while((s = br.readLine()) != null)                         //adds lines read from the file to a string and prints them    
                    instructions += (s + "\n");                                       //This string stores all the data of the file

                System.out.println("File read successfully.");
                System.out.println("***********************");

                sc.close();
                br.close();

            } else {
                  
                  System.out.println("ERROR : FILE INVALID");
                  System.out.println("***********************");

              }
        }  
    }

    public void cleaner(){
		
        String s = instructions;
        br = new BufferedReader(new StringReader(s));

        

    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}
