import java.io.*;
import java.util.Scanner;

public class assembler {
    public static void filehandler() {
        System.out.println("Enter file name");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        File filereader = new File("./" + filename + ".asm");
        String content;
        String line;
        try {
            Scanner scn = new Scanner(filereader);//var
            // BufferReader scn = new  BufferedReader(new FileReader(filereader));
            while (scn.hasNextLine()) {
                line = scn.readline();
                content+=line;   
            }
           

            sc.close();//var me input the whole file
        } catch (Exception e) {
            System.out.println("File not found");
            System.out.println(e);
        }
    }


    public static void main(String []args) {
            filehandler();
        }
      
    
 }