#include<stdio.h>
#include<string.h>

char file_name[50] = {'\0'}, line[1000], instructions[1000000];

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
    }while(*sp++ = *dp++);      //puts next valid character(*dp) in the location pointed by (*sp), thus altering string. Increments both to next char, until NULL.
    
    dp = sp = instructions;
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
    
    
    printf("\nCleaner executed  successfully.");
    printf("\n**************************************");
}


int main (void) {

    file_handler();
    cleaner();
    return 0;
}