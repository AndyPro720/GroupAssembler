import java.io.*;
import java.util.Scanner;

class AssemblerDesign{
    //code to create new file
    public static void filehandler(){
    File myfile=new File("assemblerfile.txt");
    try {
        myfile.createNewFile();
    } catch (IOException e) {
        System.out.println("Unable to create a file");
        
    }
    //code too read a file
    File filereader=new File("assemblerfile.txt");
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