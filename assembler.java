import java.io.*;
import java.util.Scanner;

class AssemblerDesign{
   
    //code to read a file
    public void filehandler(){
    File filereader=new File("C:/Users/mites/OneDrive/Documents/GitHub/GroupAssembler/code.asm");
     try {
        Scanner sc=new Scanner(filereader);
        while(sc.hasNextLine()){
            String line=sc.nextLine();
            System.out.println(line);
        }
        sc.close();
     } catch (FileNotFoundException e) {
        System.out.println("File not found");
     }

}
    
}   



public class assembler{
    public static void main(String []args){
       AssemblerDesign obj=new AssemblerDesign();
       obj.filehandler();
      
    } 
 }