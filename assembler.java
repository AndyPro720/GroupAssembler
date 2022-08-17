import java.io.*;
import java.util.*;

public class Assembler {

    public void fileHandler() throws FileNotFoundException {
        
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();    
        System.out.println("Enter the filename : " + file);   
        File f = new File(file + ".asm");       //Follow the API for file input
        
        Scanner scan = new Scanner(new FileReader(f));

      

            while(scan.hasNext()) {
                
                System.out.print(scan.nextLine());
            }

        
        
    }

    public static void main(String args[]) throws FileNotFoundException {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}