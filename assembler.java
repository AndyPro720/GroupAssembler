import java.io.*;
import java.util.*;

public class Assembler {

    public char ch;

    public void fileHandler() throws FileNotFoundException, IOException {
        
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();    
        System.out.println("Enter the filename : " + file);   
        File f = new File(file + ".asm");       //Creates instance of the file taken from input
        
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            
        while(bis.available() > 0) {     //reads bytes from file as long as they're available
                
            ch = (char)bis.read();
            System.out.print(ch);       //prints file contentss

        }

        sc.close();
        bis.close();
        
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}