#include<iostream> 
#include<fstream> 
#include<sstream>
#include<algorithm>
#include<unordered_map>
#include <ctype.h>

class assemble {
   
   std::string instructions; 
   std::unordered_map <std::string, std::string> symbol;
   
   public:
   assemble();
   bool io_state = false;

   int file_handler() {     //method for file I/O management

      std::string file_name;
      if (io_state == 0) {  //Input file handler
            std::fstream i_file_handle;
             while (true)   //open file    
               {
                  std::cout << "Input the file name (without extension)    <----->       ctrl+c to exit\n";
                  std::getline(std::cin, file_name);
                  i_file_handle.open(file_name+".asm", std::ifstream::in); 

                     if (i_file_handle.seekg(0, std::ios::end)) {   //get data in instructions and close file. 
                        
                        instructions.resize(i_file_handle.tellg());
                        i_file_handle.seekg(std::ios::beg);
                        i_file_handle.read(&instructions[0], instructions.size()); //read the file and store it

                        std::cout << "File Read Successfully. \n" << "********************** \n" ; 
                        i_file_handle.close(); 
                        return 0;
                        }

                     else {
                         std::cout << "Couldn't find the named file.\n" << "********************** \n" ;
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

   void cleaner() {      //method to clear whitespace and comments from code

      instructions.erase(std::remove_if(instructions.begin(), instructions.end(), ::isblank), instructions.end());   //clears all whitespace

      std::istringstream stream(instructions);
      std::string line;
      instructions.clear();

      while(std::getline(stream, line)) {   //cleans comments
         
         if (line.find("//") != std::string::npos) {
            if(line.find("//") == 0) continue;
            instructions += line.substr(0, line.find("//")) + '\n'; 
         }
         else {
             instructions += line + '\n';
         }
      }

      instructions.erase(instructions.end()-1);  //trims the last newline

      std::cout << "Instructions Cleaned \n" << "********************** \n";
   }
   
   void first_pass_labels() {     //parses through instructions and stores labels and their values
 
      std::istringstream stream(instructions);
      std::string line;
      int count = 0;  
      instructions.clear();
      
      while(std::getline(stream, line)) {        

         if(line.find('(') != std::string::npos) {   //stores labels
            symbol[line.substr(1, line.find(')')-1)] = std::to_string(count);
         }

         else {    //regular instructions
            instructions += line + '\n';
            count++;
         }
      }
      instructions.erase(instructions.end()-1);  //trims the last newline
      std::cout << "First Pass Completed \n" << "********************** \n";
   }
   
   void second_pass_var() {   //stores and replaces variables. Replaces label mentions
      
      std::istringstream stream(instructions);
      std::string line;
      int reg = 15;
      instructions.clear();
      
      while(std::getline(stream, line)) {
         if(line[0] == '@' && !std::isdigit(line[1])) {    //if A instruction and non register declaration

            symbol.insert(std::pair<std::string, std::string>(line.substr(1, line.size()), std::to_string(++reg)));     //inserts variable and value, ignores duplicates
            line.replace(1, line.size(), symbol[line.substr(1, line.size())]);   //replaces label/variables with numerical value
            instructions += line + '\n';
         }  

         else instructions += line + '\n';
      }

      std::cout << "Second Pass Completed, variables stored \n" << "********************** \n";
   }

}; 





int main()
    {

       assemble code;
       
       code.file_handler();
       code.cleaner();
       code.first_pass_labels();
       code.second_pass_var();
       return 0;
    }


assemble::assemble() {  //constructor for storing maps
   symbol  = {
      {"R0", "0"},
      {"R1", "1"},
      {"R2", "2"},
      {"R3", "3"},
      {"R4", "4"},
      {"R5", "5"},
      {"R6", "6"},
      {"R7", "7"},
      {"R8", "8"},
      {"R9", "9"},
      {"R10", "10"},
      {"R11", "11"},
      {"R12", "12"},
      {"R13", "13"},
      {"R14", "14"},
      {"R15", "15"},
      {"SCREEN", "16384"},
      {"KBD", "24576"},
      {"SP", "0"},
      {"LCL", "1"},
      {"ARG", "2"},
      {"THIS", "3"},
      {"THAT", "4"}, 
   };
}