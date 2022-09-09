#include<iostream> 
#include<fstream>
#include<sstream>

   
int main()
    {
        std::fstream file_handle;
        file_handle.open("test.txt", std::ios::in); 
        
        std::stringstream ss;
        int test = 32;

        ss << test;
        std::cout << ss.str(); 

        return 0;
    }

