//if KBD!=0 set value -1 to 100 register below @SCREEN
//else KBD=0 they should have value 0

//n=screen+100
//if(KBD!=0){
//FOR(i=Screen;i<n;i++){
//     arr[i]=-1} }
//else{FOR(i=Screen;i<n;i++){
//     arr[i]=0} }

// screen[100]=-1 or 0

(INFINITE)

@100
D=A
@i
M=D

@SCREEN
D=A 

@sum
M=D  

@KBD
D=M


@ELSE
D;JEQ

(IF)
@sum
A=M    
M=-1

@sum
M=M+1

@i
MD=M-1

@IF
D;JGE 

@INFINITE
0;JMP


(ELSE)
@sum
A=M     
M=0

@sum
M=M+1

@i
MD=M-1

@ELSE
D;JGE


@INFINITE
0;JMP