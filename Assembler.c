#include<stdio.h>
#include<string.h>
#include<ctype.h>

char file_name[50] = {'\0'}, line[1000], instructions[1000000];
int sym_index = 23;
struct map 
{
   char key[1000][100];
   char value[1000][10]; 
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
        } 
};

int mapf(char *id, char *value) {    //searches for string in key and puts value in second argument. Returns 1 if found, else 0.

    for(int j = 0; j < sym_index; j++) {
       if(!strcmp(symbol_table.key[j], id)) {
            strcpy(value, symbol_table.value[j]);
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
            
            strcpy(symbol_table.key[sym_index], label);
            strcpy(symbol_table.value[sym_index++], itos(line));
            //printf("%s %s\n", symbol_table.key[sym_index-1], symbol_table.value[sym_index-1]);
            
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

           if(mapf(identifer, value)) {    //if id found, write value after @
             while(*v != '\0') *sp++ = *v++;
           }
           
           else {       //store variable and value, write after @ 
            strcpy(symbol_table.key[sym_index], identifer);
            strcpy(symbol_table.value[sym_index++], itos(++reg));
            
            strcpy(value, itos(reg));
            while(*v != '\0') *sp++ = *v++;
           }
       }    
    }while(*sp++ = *dp++);

    printf("\nSecond Pass executed successfully.");
    printf("\n**************************************");
    }  

int main (void) {

    file_handler();
    cleaner();
    first_pass_labels();
    second_pass_var();
    return 0;
}