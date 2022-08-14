#include<iostream> 
#include<fstream> 

class assemble {
 
   std::string file_name;
   std::string raw_instructions; 
   bool io_state = false;
   
   public:

   int file_handler() {     //method for file I/O management

      if (io_state == 0) {  //Input file handler
            std::fstream i_file_handle;
             while (true)    
               {
                  std::cout << "Input the file name (without extension)    -----       ctrl+c to exit\n";
                  std::getline(std::cin, file_name);
                  i_file_handle.open(file_name+".asm", std::ifstream::in); 

                     if (i_file_handle.is_open()) {   //get input in raw_instructions and close file. 
                        std::cout << "File Opened sucessfully \n";
                        //get input in var
                        i_file_handle.close(); 
                        return 0;
                        }

                     else {
                         std::cout << "Couldn't find the named file.\n";
                        }
                }
         }
      else {  //Output file handler
         std::fstream o_file_handle;
         o_file_handle.open(file_name+".hack", std::ofstream::out | std::ofstream::trunc);
         std::cout << "Created the output file, processing assembly";
      }
   }   
}; 




int main()
    {
       assemble code;
       
       code.file_handler();
       return 0;
       return 0;
    }