import java.io.*;
import java.util.*;

public class Assembler {

    public String s = "";

    public void fileHandler() throws FileNotFoundException, IOException {   //File handler (accesess & reads file)
        
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();    
        System.out.println("File : " + file + ".asm");   
        File f = new File(file + ".asm");                               //Creates instance of the file taken from input
        
        BufferedReader br = new BufferedReader(new FileReader(f));
        String str;
            
        while((str = br.readLine()) != null)                         //adds lines read from the file to a string and prints them    
            s += (str + "\n");                                       //This string stores all the data of the file

        System.out.println("File read successfully.");
        System.out.println("***********************");

        sc.close();
        br.close();
        
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}
