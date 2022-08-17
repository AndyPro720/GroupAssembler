import java.io.*;
import java.util.Scanner;

public class assembler {
    public static void filehandler(){
        System.out.println("Enter file name");
        Scanner sc=new Scanner(System.in);
        String filename=sc.nextLine();
        File filereader=new File("./"+filename+".asm");
        try {
            Scanner scn=new Scanner(filereader);//var
            //  String read=scn.readline();
            while(scn.hasNextLine()){
                String line=scn.nextLine();
                System.out.println(line);
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