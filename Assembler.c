#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>

//declaring all global variables and functions

char file_name[50] = {'\0'}, line[1000], instructions[1000000], binary[1000000]; 
int sym_index = 23;
struct map 
{
   char key[1000][100];
   char value[1000][10]; 
   int index;
};

char * itos(int number) {   //converts int to string and returns value 
    static char string[100];  
    snprintf(string, sizeof(string), "%d", number);   
    return string;
}

struct map symbol_table = {
   .key = {
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
        }, 
   
  .value = {
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

   .index = 23
};

struct map dest = {

    .value = {
         "000",
         "001",
         "010",
         "011",
         "100",
         "101",
         "110",
         "111"
        },
    .key = {
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

struct map jump = {

    .value = {
         "000",
         "001",
         "010",
         "011",
         "100",
         "101",
         "110",
         "111"
        },
    .key = {
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

struct map comp = {

    .value = {
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
    .key = {
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

int mapf(char *id, char *value, struct map *table) {    //searches for id in key and puts value in second argument(if not found puts NULL) 
                                                        //Returns 1 if found, else 0.
    for(int j = 0; j < table->index; j++) {
       if(!strcmp(table->key[j], id)) {
            strcpy(value, table->value[j]);
            return 1;
       } 
    }

    *value = '\0';
    return 0;
}

void file_handler (void) {
   FILE * fp;
   char  *p;

   do   //Open the file
   {
    printf("Please Input file name, without extension. (Ctrl+C to exit)\n");
    fgets(file_name, sizeof(file_name), stdin);

    p = strrchr(file_name, '\n');  //remove the trailing newline
    size_t index = p - file_name;
    file_name[index] = '\0';

    strcat(file_name, ".asm"); //appending .asm extension
    fp = fopen(file_name, "r");


    } while(fp == NULL) ;
   
   //read the file, and store in instructions,  use binary?
   while(fgets(line, sizeof(line), fp) != NULL) {
       strcat(instructions, line);
   }

    fclose(fp);

    printf("\nFile read successfully.");
    printf("\n**************************************");
}

void cleaner(void) {  //removes whitespace and comments

   //should create a copy 
    char *dp, *sp;
    dp = sp = instructions;

    do {        //clear empty space and comments

        while(*dp == ' ' || *dp == '\r' || *dp == '\f' || *dp == '\v'|| *dp == '\t')  dp++;   //skip through whitespace characters

        while(*dp == '/') {         //skip till next line if comments(//)
            if(*(dp+1) == '/') {
               while(*dp != '\n') {
                dp++;
               } 
               continue;
            }
            break;
        }
    }while(*sp++ = *dp++);      //puts the character pointed by(*dp) in the location pointed by string pointer(*sp), thus altering string.
                                // Increments both to next char, until dp returns NULL.
    
    dp = sp = instructions; //reset pointers
    do {        //clear any consecutive newlines(empty lines) 

        if(instructions[0] == '\n') dp++; 

        while(*dp == '\n') {        
            if(*(dp-1) == '\n') { 
                dp++;
                continue;
            }
           break; 
        }
    }while(*sp++ = *dp++);
    
    
    printf("\nCleaner executed successfully.");
    printf("\n**************************************");
}

void first_pass_labels(void) {          //Extract label and add it to symbol table with value(line number)
    char *sp, *dp, label[100], *lp;
    sp = dp = instructions;
    lp = label;
    int line = 0;
    
    
    do {
        while(*dp == '(') {        //find label 
            dp++; 
            while(*dp != ')') *lp++ = *dp++;  //store label refrence in label var 
            *lp = '\0';         
            
            strcpy(symbol_table.key[symbol_table.index], label);
            strcpy(symbol_table.value[symbol_table.index++], itos(line));
            //printf("%s %s\n", symbol_table.key[symbol_table.index-1], symbol_table.value[symbol_table.index-1]);
            
            dp += 2;   //skip ) and \n
        }  
        lp = label;  //reset label pointer

        if(*dp == '\n') line++;     //count lines

    }while(*sp++ = *dp++);     

    printf("\nFirst Pass executed successfully.");
    printf("\n**************************************");
    
}

void second_pass_var(void) {        //stores variables, replaces label and variable calls with value
    char *sp, *dp, *id, *v, identifer[100], value[100];
    sp = dp = instructions;
    int reg = 15;
    
    do {
    id = identifer, v = value; //reset pointers

        if(*dp == '@' && !(isdigit(*(dp+1)))) {    //if A instruction and not reg declaration
           *sp++ = *dp++; //write @ 

           while(*dp != '\n') *id++ = *dp++;    //extract identifier and store 
           *id = '\0';

           if(mapf(identifer, value, &symbol_table)) {    //if id found, write value after @
             while(*v != '\0') *sp++ = *v++;
           }
           
           else {       //store variable and value, write after @ 
            strcpy(symbol_table.key[symbol_table.index], identifer);
            strcpy(symbol_table.value[symbol_table.index++], itos(++reg));
            
            strcpy(value, itos(reg));
            while(*v != '\0') *sp++ = *v++;
           }
       }    
    }while(*sp++ = *dp++);

    printf("\nSecond Pass executed successfully.");
    printf("\n**************************************");
    }  

void translator(void) {     //translate instructions to 16bit binary code

    char *sp, *dp, *lp, line[1000];
    sp = instructions; //string pointer
    dp = binary;    //destination pointer
    lp = line;      //line pointer

    do{     //read by line and send for translation 
       while(*sp != '\n' && *sp != '\0')  *lp++ = *sp++;    //read line and store it (\0 condition for last line)  
       *lp = '\0';  
        lp = line;   //reset line pointer 
       
      if(line[0] == '@') {  //if A instructions
          char bin[17], *bp;
          bp = bin;

          long p = 16, i = strtol((line+1), NULL, 10);  //convert string to int/long
          
          while(p--) *bp++ = '0';   //add padding of 16 zeros 
          *bp = '\0';   

          do{       //converts to binary (gives bin in reverse, hence stored from end) 
            (*(--bp) = (i % 2) > 0 ? '1' : '0');
          }while(i/=2);
          bp = bin;

          while(*bp != '\0') *dp++ = *bp++;     //write to destination pointer 
          *dp++ = *sp;    //add line ending
      } 
      
      else {    //for C instructions
          
          char *token;
          token = strtok(line, "=;");
          while(token != NULL) {
            
            printf("%s ", token);
            token = strtok(NULL, "=;");
          }
          printf("\n");

        
      } 
      

       //printf("%s\n", line);

    }while(*sp++ != '\0');     //goes to next char if line ending is not \0 
   *dp = '\0'; 
          //printf("%s", binary);
    
}

int main (void) {

    file_handler();
    cleaner();
    first_pass_labels();
    second_pass_var();
    translator();
    return 0;
}