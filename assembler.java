import java.io.*;
import java.util.*;

public class Assembler {

    public String instructions = "";
    BufferedReader br;

    private Map<String, String> symbol = new HashMap<>();
    
    Assembler() {
    
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

    public void file_handler() {   //File handler (accesess & reads file)
        
        while(true) {
            
            Scanner sc = new Scanner(System.in);
            String in = sc.nextLine() + ".asm";   
            
            try {
            
                File f = new File(in);                                   //Creates instance of the file taken from input
                System.out.println("File : " + in);
            
                br = new BufferedReader(new FileReader(f));
                String s;
            
                while((s = br.readLine()) != null)                         //adds lines read from the file to a string
                    instructions += (s + "\n");                             //This string stores all the data of the file

                System.out.println("File read successfully.");
                System.out.println("***********************");

                sc.close();
                br.close();
                break;

            } catch(FileNotFoundException fnfe) {                                                 
                  
                  System.out.println("ERROR : FILE NOT FOUND");
                  System.out.println("***********************");

              } 
              catch(IOException ioe) {                                               
                  
                  ioe.printStackTrace();

              }
        }  
    }

    public void cleaner() {                   //method for clearing whitespace and comments
		
        String i, temp = "";

        try {
        
            br = new BufferedReader(new StringReader(instructions));

            while((i = br.readLine()) != null) {                     
  
                if(i.contains("//"))
                    temp += (i.substring(0, i.indexOf("//")) + "\n");          //removes comments
                else if(i.isEmpty())                                           //ignores empty lines between the text
                    continue;
                else
                    temp += (i + "\n");  

            } 
        } catch(IOException ioe) {
              ioe.printStackTrace();
          }
        
        instructions = temp.replaceAll("[\t ]*(.*?)[\t ]*", "").strip();       //removes whitespace, tabs, leading and trailing empty lines
       
            
        System.out.println("Cleaning....");
        System.out.println("File after cleaning : \n" + instructions);

    }

    public void first_pass_labels() {                   //removes labels from instructions and adds them to symbol table

        String temp = "";
       
        try {
        
            br = new BufferedReader(new StringReader(instructions));
            int lineno = -1;
            String i = "";
        
            while((i = br.readLine()) != null) {

                lineno++;

                if(i.startsWith("(")) {           //label found

                        symbol.put(i.substring(i.indexOf('(') + 1, i.indexOf(')')), String.valueOf((--lineno)+1));        //add to symbol table
                        continue;                //skip line
    
                }

                temp += (i + "\n");
            
            }
        } catch(IOException ioe) {
              ioe.printStackTrace();
          }

          instructions = temp.stripTrailing();

    }

    public void second_pass_var() {                    //replaces variables with corresponding address register value

        int reg = 16;
        String i, temp = "";
        
         try {
        
            br = new BufferedReader(new StringReader(instructions));

            while((i = br.readLine()) != null) {

                if(i.startsWith("@")) {                    //check if var found
                    
                    String name = i.substring(1);

                    if(name.matches("[0-9]+"))           //if addr mentioned instead of name
                        temp += (i + "\n");
                    else if(symbol.containsKey(name)) {        //if var in sybmol table then modify instruction

                        temp += (i.replace(name, symbol.get(name)) + "\n");
                        continue;

                    }
                    else {                               //else add to symbol table and modify instruction

                        symbol.put(name, String.valueOf(reg));  
                        temp += (i.replace(name, String.valueOf(reg++)) + "\n");

                    }
                }
                else {

                    temp += (i + "\n");

                }
            }
        } catch(IOException ioe) {
              ioe.printStackTrace();
          }

          instructions = temp.stripTrailing();

    }

    public static void main(String args[]) {
        
        Assembler asmb = new Assembler();

        asmb.file_handler();
        asmb.cleaner();
        asmb.first_pass_labels();
        asmb.second_pass_var();

    }
}
