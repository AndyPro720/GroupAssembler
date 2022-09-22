#include<stdio.h>
#include<string.h>

char file_name[50] = {'\0'},instructions[1000000];

void file_handler (void) {
   FILE * fp;
   char  *p, line[1000];

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

void cleaner(void) {


}


int main (void) {

    file_handler();
    return 0;
}