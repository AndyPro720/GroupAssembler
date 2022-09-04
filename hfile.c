#include <stdio.h>

int file_handler() {
    
    FILE *file;

    char string[100];
    
    file = fopen("college.txt", "r+");
        
    printf("File created/opened successfully\n");
    fprintf(file, "%s", "School");

    fscanf(file,"%s", string);
    printf("%s", string);
    
    fclose(file);

    return 0;
}

int main() {
    
    file_handler();

    return 0;
}