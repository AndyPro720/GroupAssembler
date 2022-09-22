#include<stdio.h>

int main(void) {
char string[] = "this  is  for  the strtok \n  function and its functionality";
    char* d = string;
    char* s = string;

    do {
        while (*d == ' ' || *d == '\n') {
            d++;
        }
    } while (*s++ = *d++);
    
    printf("%s", string);
    
}

