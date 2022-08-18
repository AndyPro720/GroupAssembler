import java.io.*;
import java.util.*;

public class Assembler {

    public String instructions = "";
    BufferedReader br;

    public void file_handler() throws IOException {   //File handler (accesess & reads file)
        
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

    public void cleaner() throws IOException {                   //method for clearing whitespace and comments
		
        String i, temp = "";
        br = new BufferedReader(new StringReader(instructions));

        while((i = br.readLine()) != null) {                     

            if(i.contains("//"))
                temp += (i.substring(0, i.indexOf("//")) + "\n");          //removes comments
            else
                temp += (i + "\n");  

        } 

        instructions = temp.replaceAll("[\t ]*(.*?)[\t ]*", "");           //removes whitespace, tabs
        instructions = instructions.replaceAll("(?m)^[ \t]*\r?\n", "");    //removes empty lines
        instructions = instructions.trim();                                //removes leading & trailing empty lines

        System.out.println("Cleaning....");
        System.out.println("File after cleaning : \n" + instructions);

    }

    public static void main(String args[]) throws IOException {
        
        Assembler asmb = new Assembler();

        asmb.file_handler();
        asmb.cleaner();

    }
    
}
