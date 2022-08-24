import java.io.*;
import java.util.Scanner;

public class Assembler {

   public static String instructions;


    public static void file_handler() {
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
            while (scn.hasNextLine()) {
                String line=scn.nextLine();
                instructions = instructions+line+'\n';
            }
            //file is stored in content
           sc.close();
        } 
        catch (Exception e) {
            System.out.println("File not found");
            System.out.println(e);
        }
        
    }


    public static void cleaner(){ 

         //intializing variables
         String temp ="";
         String clean="";
        
            Scanner sci = new Scanner(instructions);
            
            while(sci.hasNextLine()) {
                temp =sci.nextLine();                     
                if(temp.contains("//")){
                    clean+= (temp.substring(0, temp.indexOf("//")) + "\n");   
                }  
                else{
                    clean += (temp + "\n");
                }   
            } 
        
    
       

          System.out.println("Clearing....");
        
          instructions =clean.replaceAll("[\t ]*(.?)[\t ]", "");                                  
          instructions = instructions.strip();                                
      
          System.out.println("After clearing :\n " + instructions);   
    }


 

    public static void main(String []args) {
        //filehandler method is used
         file_handler();
        //cleaning method
        cleaner();
        }
      
    
 }