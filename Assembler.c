#include <stdio.h>
#include <string.h>

int access = 1;                             //binary var determines file to be read or written to
char in[10], instructions[10000], temp[100];

void file_handler(void) {                   //File handler (for creating, accessing, reading & writing files)
    
    if(access) {                             //For input of assembly file
    
        FILE * asm_file;                   
        
        while(1) {
        
            printf("Enter name of the assembly file : ");
            scanf("%s", in);
            
            if ((asm_file = fopen(strcat(in, ".asm"), "r")) != NULL) {  

                char line[100];

                while (fgets(line, 100, asm_file) != NULL)        //read contents from file and stores in instructions
                    strcat(instructions, strcpy(temp, line));

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

        if((hack_file = fopen(strcat(in, ".hack"), "w+")) != 0) {   //Writes machine instructions to hack file

            fprintf(hack_file, "%s", instructions);
            fclose(hack_file);

        }
        else {
            printf("ERROR in generating hack file.");
        }
        
        printf("Assembled Successfully and Object File Generated.");

    }
}


int main() {
    
    file_handler();

    return 0;
}