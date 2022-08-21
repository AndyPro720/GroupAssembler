import java.io.*;
import java.util.Scanner;

public class assembler {

    public static void filehandler() {
        //enter the name of file you want to access
        System.out.println("Enter file name");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        //reading the file
        File filereader = new File("./" + filename + ".asm"); 
        System.out.println("File opened");
        //exception handling
        try {
            Scanner scn = new Scanner(filereader);//var
            String content="";
            while (scn.hasNextLine()) {
                String line=scn.nextLine();
                content = content+line+'\n';
            }
            //file is stored in content
           System.out.println(content);
           sc.close();
        } 
        catch (Exception e) {
            System.out.println("File not found");
            System.out.println(e);
        }
    }


    public static void Cleaner() {

         //enter the name of file you want to access
         System.out.println("Enter file name");
         Scanner sc = new Scanner(System.in);
         String filename = sc.nextLine();

         //reading the file
         File filereader1 = new File("./" + filename + ".asm"); 
         System.out.println("File opened");

         //intializing variables
         String instructions;
         String a ="";
        
        //expection handling
        try {
        
            Scanner sci = new Scanner(filereader1);
            
            while(sci.hasNextLine()) {
                a +=sci.hasNextLine();                     
                if(a.contains("//")){
                    a= (a.substring(0, a.indexOf("//")) + "\n");   
                }      
                
                else{
                    a += (a + "\n"); 
                } 
            } 
        
    
        } catch(Exception e) {
              System.out.println(e);
          }

          System.out.println("Clearing....");
        
          instructions =a.replaceAll("[\t ]*(.?)[\t ]", "");                                  
          instructions = instructions.trim();                                
      
          System.out.println("After clearing :\n " + instructions);   
    }


 

    public static void main(String []args) {
        //filehandler method is used
          // filehandler();
        //cleaning method
           assembler.Cleaner();
        }
      
    
 }