import java.io.*;
import java.util.*;
import java.lang.*;

public class Assembler {

    public String instructions = "";
    BufferedReader br;

    private Map<String, String> symbol = new HashMap<>();
    private Map<String, String> dest = new HashMap<>();
    private Map<String, String> jump = new HashMap<>();
    private Map<String, String> comp = new HashMap<>();

    boolean access = true;

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

    public void file_handler() {                         //File handler (for creating, accessing, reading & writing files)

        if(access) {                                    //For input of assembly file
        
            while(true) {
            
                System.out.print("Enter name of the assembly file : ");
                Scanner sc = new Scanner(System.in);
                String in = sc.nextLine() + ".asm";   
            
                try {
            
                    File f = new File(in);                                   //Creates instance of the file taken from input
            
                    br = new BufferedReader(new FileReader(f));
                    String s;
            
                    while((s = br.readLine()) != null)                         //adds lines read from the file to a string
                        instructions += (s + "\n");                             //This string stores all the data of the file

                    System.out.println("File read successfully.");
                    System.out.println("\n**************************************\n");

                    sc.close();
                    br.close();
                    break;

                } catch(FileNotFoundException fnfe) {                                                 
                  
                    System.out.println("ERROR : FILE NOT FOUND");
                    System.out.println("\n**************************************\n");

                } 
                catch(IOException ioe) {                                               
                    ioe.printStackTrace();
                }
            }
        }  
        else {                                                      //For output of assembly file

            try {
            
                PrintWriter w = new PrintWriter("out.hack");       //For writing machine code into '.hack' file
                w.println(instructions);
                w.close();
            
            } catch(FileNotFoundException fnfe) {
                  fnfe.printStackTrace();
            }

           System.out.println("Assembled Successfully and Object File Generated.");

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
        System.out.println("\n**************************************\n");

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

          System.out.println("First pass Parsing.....");
          System.out.println("\n**************************************\n");

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

          System.out.println("Second pass Parsing.....");
          System.out.println("\n**************************************\n");

    }

    public boolean translator() {                            //assembly -> machine-level binary

        String temp = "";
        String i = "";
        
        try {
        
            br = new BufferedReader(new StringReader(instructions));

            while((i = br.readLine()) != null) {
                
                if(i.startsWith("@"))                                //A instruction translation
                    temp += (String.format("%016d", Long.valueOf((Long.toBinaryString(Long.valueOf(i.substring(1)))))) + "\n");
                else {                                               //C instruction translation
                        
                    String destination = "";
                    String jmp = "";
                    String compute = "";

                    if(i.contains("=") && i.contains(";")) {              //dest=comp;jump
                        
                        destination = i.substring(0, i.indexOf('='));
                        compute = i.substring(i.indexOf('=') + 1, i.indexOf(';'));
                        jmp = i.substring(i.indexOf(';') + 1);

                        temp += ("111" + comp.get(compute) + dest.get(destination) + jump.get(jmp) + "\n");
                        continue;

                    }
                    if(i.contains("=")) {                              //dest=comp

                        destination = i.substring(0, i.indexOf('='));
                        compute = i.substring(i.indexOf('=') + 1);

                        temp += ("111" + comp.get(compute) + dest.get(destination) + jump.get(jmp) + "\n");
                        continue;

                    }
                    if(i.contains(";")) {                            //comp;jump

                        compute = i.substring(0, i.indexOf(';'));
                        jmp = i.substring(i.indexOf(';') + 1);

                        temp += ("111" + comp.get(compute) + dest.get(destination) + jump.get(jmp) + "\n");
                        continue;

                    }
                    
                    compute = i;                                    //only comp
                    temp += ("111" + comp.get(compute) + "\n");  

                }
            }
        } catch(IOException ioe) {
              ioe.printStackTrace();
          }
        
        instructions = temp.stripTrailing();

        System.out.println("Translation to Machine Language.....");
        System.out.println("\n**************************************\n");
        
        return false;

    }

    public static void main(String args[]) {
        
        Assembler asmb = new Assembler();

        asmb.file_handler();
        asmb.cleaner();
        asmb.first_pass_labels();
        asmb.second_pass_var();

        asmb.access = asmb.translator();
        asmb.file_handler();

    }
}
