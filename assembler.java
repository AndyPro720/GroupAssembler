import java.io.*;
import java.util.*;

public class Assembler {

    public String s = "";

    public void fileHandler() throws FileNotFoundException, IOException {
        
        Scanner sc = new Scanner(System.in);
        String file = sc.nextLine();    
        System.out.println("File : " + file + ".asm");   
        File f = new File(file + ".asm");                               //Creates instance of the file taken from input
        
        BufferedReader br = new BufferedReader(new FileReader(f));
        String str;
            
        while((str = br.readLine()) != null) {                         //adds lines read from the file to a string and prints them
                
            System.out.println(str);       
            s += (str + "\n");                                         //This string stores all the data of the file

        }
        System.out.println("STRING CHECK : \n" + s);                     /*Output on dry run (also prints the string in whic file data is stored)
                                                                        code
                                                                        File : code.asm
                                                                        ECHO is on.
                                                                        Sadfh;kvhc
                                                                        askdfhsa;j
                                                                        STRING CHECK : 
                                                                        ECHO is on.
                                                                        Sadfh;kvhc
                                                                        askdfhsa;j
                                                                        */

        sc.close();
        br.close();
        
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}
