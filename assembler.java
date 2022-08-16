import java.io.*;

public class Assembler {

    public void fileHandler() {
        
        File f = new File("/Users/apple/Documents/GitHub/GroupAssembler/code.asm");
        InputStream is = null;
        BufferedInputStream bis = null;

        try {
          
            is = new FileInputStream(f);
            bis = new BufferedInputStream(is);

            while(bis.available() > 0)
                System.out.print((char)bis.read());

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
         
            try {
            
                if(is != null && bis != null) {
                
                    is.close();
                    bis.close();

                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }

        } 
        
    }

    public static void main(String args[]) {
        
        Assembler asmb = new Assembler();

        asmb.fileHandler();

    }
    
}