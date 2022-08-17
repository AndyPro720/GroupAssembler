import java.io.*;
import java.util.*;

public class Assembler {

    public String s;

    public void fileHandler() throws FileNotFoundException, IOException {
        
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();    
        System.out.println("Enter the filename : " + file);   
        File f = new File(file + ".asm");       //Creates instance of the file taken from input
        
        BufferedReader br = new BufferedReader(new FileReader(f));
        s = br.readLine();
            
        while(s != null) {     //reads bytes from file as long as they're available
                
            System.out.println(s);       //prints file contents
            s = br.readLine();

        }

        sc.close();
        br.close();
        
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}