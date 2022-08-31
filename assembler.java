import java.io.*;
import java.util.*;


public class Assembler {

   public static String instructions="";

   public  Map<String,String> symbol = new HashMap<String,String>();
   public  Map<String,String> dest = new HashMap<String,String>();
   public  Map<String,String> comp = new HashMap<String,String>();
   public  Map<String,String> jump = new HashMap<String,String>();

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

    dest.put("", "000");
    dest.put("M", "001");
    dest.put("D", "010");
    dest.put("MD", "011");
    dest.put("A", "100");
    dest.put("AM", "101");
    dest.put("AD", "110");
    dest.put("AMD", "111");

    jump.put("", "000");
    jump.put("JGT", "001");
    jump.put("JEQ", "010");
    jump.put("JGE", "011");
    jump.put("JLT", "100");
    jump.put("JNE", "101");
    jump.put("JLE", "110");
    jump.put("JMP", "111");

    comp.put("0", "0101010");
    comp.put("1", "0111111");
    comp.put("-1", "0111010");
    comp.put("D", "0001100");
    comp.put("A", "0110000");
    comp.put("M", "1110000");
    comp.put("!D", "0001101");
    comp.put("!A", "0110001");
    comp.put("!M", "1110001");
    comp.put("-D", "0001111");
    comp.put("-A", "0110011");
    comp.put("-M", "1110011");
    comp.put("D+1", "0011111");
    comp.put("A+1", "0110111");
    comp.put("M+1", "1110111");
    comp.put("D-1", "0001110");
    comp.put("A-1", "0110010");
    comp.put("M-1", "1110010");
    comp.put("D+A", "0000010");
    comp.put("D+M", "1000010");
    comp.put("D-A", "0010011");
    comp.put("D-M", "1010011");
    comp.put("A-D", "0000111");
    comp.put("M-D", "1000111");
    comp.put("D&A", "0000000");
    comp.put("D&M", "1000000");
    comp.put("D|A", "0010101");
    comp.put("D|M", "1010101");

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
        
    
       

        //   System.out.println("Clearing....");
        
          instructions =clean.replaceAll("[\t ]*(.*?)[\t ]*","");                                 
          instructions = instructions.strip();                                
      
        //   System.out.println("After clearing :\n" + instructions);   
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
            // System.out.println("\n"+"\n"+instructions);
            
           

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
                     if(var.matches("[0-9]+")) {
                        // System.out.println(".");
                         temp += (str + "\n");
 
                     }         
                    else if(symbol.containsKey(var))  { 
                        temp+=(str.replace(var, String.valueOf(reg))+"\n");  
                      
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
         // System.out.println(instructions);
        //  symbol.forEach((key, value) -> System.out.println(key +"|"  + value));
        }

        public void translator() {                           

            String temp = "";
            String str = "";
            
                Scanner sc = new Scanner(instructions);

                while(sc.hasNextLine()) {

                    str=sc.nextLine();                                            
                            
                        String destination = "";
                        String jmp = "";
                        String compute = "";
    
                        if(str.contains("=") && str.contains(";")) {              
                            
                            destination = str.substring(0, str.indexOf('='));
                            compute = str.substring(str.indexOf('=')+1 , str.indexOf(';'));
                            jmp = str.substring(str.indexOf(';')+1 );
    
                            temp += ("111" + comp.get(compute) + dest.get(destination) + jump.get(jmp) + "\n");
                            
    
                        }
                        else if(str.contains("=")) {                             
    
                            destination = str.substring(0, str.indexOf('='));
                            compute = str.substring(str.indexOf('=')+1 );
    
                            temp += ("111" + comp.get(compute) + dest.get(destination) + jump.get(jmp) + "\n");
                          
    
                        }
                        else if (str.contains(";")) {                           
    
                            compute = str.substring(0, str.indexOf(';'));
                            jmp = str.substring(str.indexOf(';')+1);
    
                            temp += ("111" + comp.get(compute) + dest.get(destination) + jump.get(jmp) + "\n");
                            
    
                        }
                        else{
                            compute = str;                                   
                            temp += ("111" + comp.get(compute) + "\n");  
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
       //translator
       obj.translator();
        }
      
    
 }