import java.io.*;
import java.util.*;


public class Assembler {

   public static String instructions="";

   public  Map<String,String> symbol = new HashMap<String,String>();

  public Assembler() {
    
    symbol.put("R0", "0");
    symbol.put("R1", "1");
    symbol.put("R2", "2");
    symbol.put("R3", "3");
    symbol.put("R4", "4");
    symbol.put("R5", "5");
    symbol.put("R6", "6");
    symbol.put("R7", "7");
    symbol.put("R8", "8");
    symbol.put("R9", "9");
    symbol.put("R10", "10");
    symbol.put("R11", "11");
    symbol.put("R12", "12");
    symbol.put("R13", "13");
    symbol.put("R14", "14");
    symbol.put("R15", "15");
    symbol.put("SCREEN", "16384");
    symbol.put("KBD", "24576");
    symbol.put("SP", "0");
    symbol.put("LCL", "1");
    symbol.put("ARG", "2");
    symbol.put("THIS", "3");
    symbol.put("THAT", "4");

    }
    
   


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
                else if(temp.isBlank()){
                    continue;
                }
                else{
                    clean += (temp + "\n");
                }   
            } 
        
    
       

          System.out.println("Clearing....");
        
          instructions =clean.replaceAll("[\t ]*(.*?)[\t ]*","");                                 
          instructions = instructions.strip();                                
      
          System.out.println("After clearing :\n" + instructions);   
    }

    public  void first_pass_labels() {                  

        String temp = "";
        int line=-1;

        Scanner sci = new Scanner(instructions);
            String str = "";
        
            while(sci.hasNextLine()) {
                str=sci.nextLine();
                line++;
                

                if(str.startsWith("(")) {           
                 symbol.put(str.substring(str.indexOf('(')+1, str.indexOf(')')), String.valueOf((--line)+1));       
                continue;         
                }
               
                temp += (str + "\n");
                
            }

            instructions= temp.strip();
            System.out.println("\n"+"\n"+instructions);
            symbol.forEach((key, value) -> System.out.println(key +"|"  + value));
           

    }

    public void second_pass_var() {                   

        int reg = 16;
        String str="";
        String  temp = "";
        
         
        
            Scanner sci = new Scanner(instructions);

            while(sci.hasNextLine()) {
                str=sci.nextLine();

                if(str.startsWith("@")) {
                    String var=str.substring(1); 
                    if(symbol.containsKey(var))  { 
                        temp+=(str.replace(var, String.valueOf(reg))+"\n");  
                        continue; 
                    }else if(var.matches("0-9")){
                        temp += (str + "\n");

                    }         
                
                else{
                    symbol.put(var, String.valueOf(reg));
                    temp+=(str.replace(var, String.valueOf(reg++))+"\n");

                }
            }

                else {

                    temp += (str + "\n");

                }
            
            }

          instructions = temp.stripTrailing();
          System.out.println(instructions);
        }

    



 

    public static void main(String []args) {
        Assembler obj=new Assembler();
        //filehandler method is used
          file_handler();
        // //cleaning method
         cleaner();
        // //first pass
         obj.first_pass_labels();
        //second pass
       obj.second_pass_var();
        }
      
    
 }