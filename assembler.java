import java.io.*;
import java.util.Scanner;

public class assembler {
   

    public static String filehandler() {
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
        return "Done";
    }


    public static void Cleaner(){ 

         //intializing variables
         String instructions=filehandler();
         String temp ="";
        
        //expection handling
        try {
        
            Scanner sci = new Scanner(instructions);
            
            while(sci.hasNextLine()) {
                temp +=sci.nextLine();                     
                if(temp.contains("//")){
                    temp+= (temp.substring(0, temp.indexOf("//")) + "\n");   
                }     
            } 
        
    
        } catch(Exception e) {
              System.out.println(e);
          }

          System.out.println("Clearing....");
        
          instructions =temp.replaceAll("[\t ]*(.?)[\t ]", "");                                  
          instructions = instructions.strip();                                
      
          System.out.println("After clearing :\n " + instructions);   
    }


 

    public static void main(String []args) {
        //filehandler method is used
        // filehandler();
        //cleaning method
           assembler.Cleaner();
        }
      
    
 }