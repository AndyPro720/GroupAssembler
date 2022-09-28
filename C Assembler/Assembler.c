#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>


bool access = true;                             //binary var determines file to be read or written to
char in[35] = "", instructions[1000000] = "", logs[1000000] = "";
int key_list = 23;

struct symbol_table {

    char k[1000][50];
    char v[1000][6];

};
struct symbol_table symbol = {

    .v = {
         "0",
         "1",
         "2",
         "3",
         "4",
         "5",
         "6",
         "7",
         "8",
         "9",
         "10",
         "11",
         "12",
         "13",
         "14",
         "15",
         "16384",
         "24576",
         "0",
         "1",
         "2",
         "3",
         "4"
        },
    .k = {
         "R0",
         "R1",
         "R2",
         "R3",
         "R4",
         "R5",
         "R6",
         "R7",
         "R8",
         "R9",
         "R10",
         "R11",
         "R12",
         "R13",
         "R14",
         "R15",
         "SCREEN",
         "KBD",
         "SP",
         "LCL",
         "ARG",
         "THIS",
         "THAT"
        }
};

struct Dest {

    char k[8][4];
    char v[8][4];

};
struct Dest dest = {

    .v = {
         "000",
         "001",
         "010",
         "011",
         "100",
         "101",
         "110",
         "111"
        },
    .k = {
         "",
         "M",
         "D",
         "MD",
         "A",
         "AM",
         "AD",
         "AMD"
        }
};

struct Jump {

    char k[8][4];
    char v[8][4];

};
struct Jump jump = {

    .v = {
         "000",
         "001",
         "010",
         "011",
         "100",
         "101",
         "110",
         "111"
        },
    .k = {
         "",
         "JGT",
         "JEQ",
         "JGE",
         "JLT",
         "JNE",
         "JLE",
         "JMP"
        }
};

struct Comp {

    char k[28][4];
    char v[28][8];

};
struct Comp comp = {

    .v = {
         "0101010",
         "0111111",
         "0111010",
         "0001100",
         "0110000",
         "1110000",
         "0001101",
         "0110001",
         "1110001",
         "0001111",
         "0110011",
         "1110011",
         "0011111",
         "0110111",
         "1110111",
         "0001110",
         "0110010",
         "1110010",
         "0000010",
         "1000010",
         "0010011",
         "1010011",
         "0000111",
         "1000111",
         "0000000",
         "1000000",
         "0010101",
         "1010101"
        },
    .k = {
         "0",
         "1",
         "-1",
         "D",
         "A",
         "M",
         "!D",
         "!A",
         "!M",
         "-D",
         "-A",
         "-M",
         "D+1",
         "A+1",
         "M+1",
         "D-1",
         "A-1",
         "M-1",
         "D+A",
         "D+M",
         "D-A",
         "D-M",
         "A-D",
         "M-D",
         "D&A",
         "D&M",
         "D|A",
         "D|M"
        }
};


void file_handler(void) {                   //File handler (for creating, accessing, reading & writing files)
    
    if(access) {                             //For input of assembly file
    
        FILE * asm_file;                   
        
        while(1) {
        
            printf("Enter name of the assembly file : ");
            scanf("%s", in);
            char inp[35] = "";
            strcpy(inp, in);
            
            if ((asm_file = fopen(strcat(inp, ".asm"), "rb")) != NULL) {  

                char line[100];

                while (fgets(line, 100, asm_file) != NULL)        //read contents from file and stores in instructions
                    strcat(instructions, line);

                printf("\nFile read successfully.");
                printf("\n\n**************************************\n");

                fclose(asm_file);
                break;

            } else {

                printf("ERROR : FILE NOT FOUND");
                printf("\n\n**************************************\n");

            }
        }    
    }
    else {                                                     //For output of assembly file

        FILE * hack_file;
        FILE * log_file;
        char inp[35] = "";
        strcpy(inp, in);

        hack_file = fopen(strcat(inp, ".hack"), "wb+");      //Writes machine instructions to hack file

        if(hack_file == NULL) {   

            printf("Unable to create hack file.\n");
            exit(EXIT_FAILURE);

        }

        fputs(instructions, hack_file);
        fclose(hack_file);
        
        log_file = fopen(strcat(in, "_log_file.txt"), "wb+");       //Writes log file

        if(log_file == NULL) {   

            printf("ERROR in generating log file.");
            exit(EXIT_FAILURE);

        }

        fputs(logs, log_file);
        fclose(log_file);
        
        printf("Assembled Successfully and Object File Generated.");

    }
}

void cleaner(void) {                              //method for clearing whitespace and comments

    char temp[1000000] = "";
    int slash = 0;
    
    for(int i = 0; i < strlen(instructions); ++i) {

        if((instructions[i] == '/') && (slash == 0)) {    //removes comments
            
            slash = 1;
            continue;

        }
        if((instructions[i] == '/') && (slash == 1)) {
            
            slash = 2;
            continue;

        }
        if(slash == 2) {                                //ignores empty lines between the text

            if(instructions[i] == '\n') {
                
                if(instructions[i + 1] == ' ' || instructions[i + 1] == '\t' || instructions[i + 1] == '\n' ||  instructions[i + 1] == '\r' || instructions[i + 1] == '\0')
                    continue;

                else if(instructions[i+1] != '/') {

                    slash = 0;
                    strncat(temp, &instructions[i], 1);

                }
                else {

                    slash = 0;

                }
            }

            continue;

        }

        if(instructions[i] == '\n') {                   

            if(instructions[i + 1] == ' ' || instructions[i + 1] == '\t' || instructions[i + 1] == '\n' ||  instructions[i + 1] == '\r' || instructions[i + 1] == '\0')
                continue;

        }
        //removes whitespace, tabs, leading and trailing empty lines
        if(instructions[i] != ' ' && instructions[i] != '\t' &&  instructions[i] != '\r' && instructions[i] != '\0')
            strncat(temp, &instructions[i], 1);

    }

    strncpy(instructions, temp, sizeof(instructions));
    
    printf("\nCleaning....\n");
    printf("\n**************************************\n");

}

void first_pass_labels(void) {                  //removes labels from instructions and adds them to symbol table

    char temp[1000000] = "", line[6] = "";
    int lineno = -1;

    char * rest = instructions;
    char * i;
    
   while ((i = strtok_r(rest, "\n", &rest))) {
        
        char label[40] = "";
        lineno++;
        
        if((strchr(i, '(')) != NULL) {                  //label found 
            //add to symbol table
            strncpy(label, &i[1], strlen(i) - 2);
            strcpy(symbol.k[key_list], label);

            sprintf(line, "%d", (--lineno) + 1);
            strcpy(symbol.v[key_list++], line);

            continue;

        }

        strcat(temp, i);
        strcat(temp, "\n");

    }

    strcpy(instructions, temp);
    
    printf("\nFirst pass Parsing.....\n");
    printf("\n**************************************\n");

}

void second_pass_var(void) {                        //replaces variables with corresponding address register value

    int reg = 16;
    char * i = "";
    char * rest = instructions;
    char temp[1000000] = "", line[6] = "";
        
    while ((i = strtok_r(rest, "\n", &rest))) {

        if((strchr(i, '@')) != NULL) {                    //check if var found
   
            char name[50] = "";
            int f = 0;
            strncpy(name, &i[1], strlen(i) - 1);
            bool flag = true;

            if(isdigit(name[0])) {                      //if addr mentioned instead of name

                int j = 1;
                if(j == strlen(name)) 
                    flag = false;
                else {
                    for(j = 1; j < strlen(name); j++) {
                    
                        if (isdigit(name[j]))
                            flag  = false; 
                        else {
                            
                            flag = true;
                            f = 1;
                            break; 

                        }                             
                    }
                }
                if(!flag) {

                    strcat(temp, i);
                    strcat(temp, "\n");

                } 
            }
            
            if(flag) {                                    //if var in sybmol table then modify instruction
                
                for(int j = 0; j <= key_list + 1; j++) {
                    
                    if(!(strcmp(symbol.k[j], name))) {        

                        flag = false;
                        strcat(temp, "@");
                        strcat(temp, symbol.v[j]);
                        strcat(temp, "\n");
                        
                        break;
                    
                    }
                } 
            }
            
            if(flag) {                               //else add to symbol table and modify instruction

                strcpy(symbol.k[key_list], name);
                sprintf(line, "%d", reg++);
                strcpy(symbol.v[key_list++], line); 
                 
                strcat(temp, "@");
                strcat(temp, line);
                strcat(temp, "\n");
                
            }    
        }
        else {

            strcat(temp, i);
            strcat(temp, "\n");

        }
    }

    strcpy(instructions, temp);

    printf("\nSecond pass Parsing.....\n");
    printf("\n**************************************\n");

}

bool translator() {                                 //assembly -> machine-level binary

    char * i = "";
    char * rest = instructions;
    char temp[1000000] = "";

    while ((i = strtok_r(rest, "\n", &rest))) {

        if((strchr(i, '@')) != NULL) {              //A instruction translation

            char name[20] = "", binary[16] = "0000000000000000";
            int j;
            
            strncpy(name, &i[1], strlen(i) - 1);

            char * eptr;
            long x = strtol(name, &eptr, 10);

            for(j = 0; j < strlen(binary); j++) {

                binary[j] = (x % 2) + '0';  
                x /= 2;    

            }
            binary[j] = '\0';               //binary obtained in reverse format

            int len, tmp;  
            len = strlen(binary); 
       
            for (int i = 0; i < len/2; i++) {  

                tmp = binary[i];  
                binary[i] = binary[len - i - 1];  
                binary[len - i - 1] = tmp;  

            }  
            char suffix[17] = "";        
            
            strncpy(suffix, &binary[strlen(binary) - 16], 16);    //after reversing
            suffix[16] = '\0';
            
            strcat(temp, suffix);
            strcat(temp, "\n");

            strcat(logs, i);
            strcat(logs, " : ");
            strcat(logs, suffix);
            strcat(logs, "\n");

        }
        else {                      //C instruction translation

            int Jmp = 0, Dst = 0;   //flag variables for determining status of dest and jump in statement
            char destination[4] = "", jmp[5] = "", compute[8] = "";
            
            if(strchr(i, ';')) {              //if jump is present
                
                strcpy(jmp, strchr(i, ';'));
                memcpy(jmp, jmp + 1, sizeof(jmp));

                Jmp = (int)(strchr(i, ';') - i);

            }
            else
                Jmp = strlen(i);

            if(strchr(i, '=')) {              //if dest is present
                for (int p = 0; p < strlen(i); p++)  {  
                    if(i[p] == '=')  
                        break;
                    
                    destination[p] = i[p];  
                    
                } 

                Dst = (int)(strchr(i, '=') - i);

            }
            else
                Dst = -1;

            strncpy(compute, &i[Dst + 1], Jmp - Dst - 1);
            //merging comp, dest & jump
            char c_str[11] = "", c_str1[8] = "";
            strcat(c_str, "111");

            for(int j = 0; j < 28; j++) { 

                if(!strcmp(comp.k[j], compute)) {
                    
                    strcat(c_str1, comp.v[j]);

                    break;

                }
            }
            strcat(c_str, c_str1);
            strcat(temp, c_str);

            char d_str[4] = "";
            for(int j = 0; j < 8; j++) {
                
                if(!strcmp(dest.k[j], destination)) {
                    
                    strcat(d_str, dest.v[j]);
                    break;

                }
            }
            strcat(temp, d_str);

            char j_str[4] = "";
            for(int j = 0; j < 8; j++) {

                if(!strcmp(jump.k[j], jmp)) {

                    strcat(j_str, jump.v[j]);
                    break;

                }
            }

            strcat(temp, j_str);
            strcat(temp, "\n");

            strcat(logs, i);
            strcat(logs, " : ");
            strcat(logs, d_str);
            strcat(logs, " dest ");
            strcat(logs, c_str1);
            strcat(logs, " comp ");
            strcat(logs, j_str);
            strcat(logs, " jmp ");
            strcat(logs, "\n");


        }         
    }

    strcpy(instructions, temp);
    instructions[(int)(strrchr(instructions, '\n') - instructions)] = '\0';

    printf("\nTranslation to Machine Language.....\n");
    printf("\n**************************************\n");

    return false;

}

int main() {

    file_handler();
    cleaner();
    first_pass_labels();
    second_pass_var();

    access = translator();

    file_handler();

    return 0;

}