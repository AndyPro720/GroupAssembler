#include<iostream> 
#include<fstream> 

class assemble {
 
   std::string raw_instructions; 
   bool io_state = false;
   
   public:

   int file_handler() {     //method for file I/O management

      std::string file_name;
      if (io_state == 0) {  //Input file handler
            std::fstream i_file_handle;
             while (true)   //open file    
               {
                  std::cout << "Input the file name (without extension)    <----->       ctrl+c to exit\n";
                  std::getline(std::cin, file_name);
                  i_file_handle.open(file_name+".asm", std::ifstream::in); 

                     if (i_file_handle.seekg(0, std::ios::end)) {   //get data in raw_instructions and close file. 
                        
                        raw_instructions.resize(i_file_handle.tellg());
                        i_file_handle.seekg(std::ios::beg);
                        i_file_handle.read(&raw_instructions[0], raw_instructions.size()); //read the file and store it

                        std::cout << "File Read Successfully. \n"; 
                        std::cout << "************************ \n"; 
                        i_file_handle.close(); 
                        return 0;
                        }

                     else {
                         std::cout << "Couldn't find the named file.\n";
                         std::cout << "************************ \n"; 
                        }
                }
         }
      else {  //Output file handler
         std::fstream o_file_handle;
         o_file_handle.open(file_name+".hack", std::ofstream::out | std::ofstream::trunc);
         std::cout << "Created the output file, processing assembly";
         return 0;
      }
   }   
}; 




int main()
    {

       assemble code;
       
       code.file_handler();
       return 0;
    }