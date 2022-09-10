#include<iostream>
#include<fstream>
#include<sstream>
#include<algorithm>

int main() {
   std::fstream i_file_handle;
   std::string instructions;
         
                i_file_handle.open("test.txt", std::ifstream::in); 

                        i_file_handle.seekg(0, std::ios::end); 
                        instructions.resize(i_file_handle.tellg());
                        i_file_handle.seekg(std::ios::beg);
                        i_file_handle.read(&instructions[0], instructions.size()); //read the file and store it
                     
                     std::istringstream stream(instructions);
                     std::string line;
                     instructions.clear();
                     while(std::getline(stream, line)) {
                       std::replace(line.begin(), line.end(), '(', '{'); 
                       std::replace(line.begin(), line.end(), ')', '}');
                       std::replace(line.begin(), line.end(), ';', ',');
                       if(line.size() > 0) instructions += line.substr(line.find('{'), line.find(',')) + '\n';
                       else instructions += line + '\n'; 
                     }
                     std::cout << instructions;
            
return 0;

}