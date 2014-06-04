#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX		(100)
#define MAXBITS	(2)
#define MAXV	(10)
#define MINV		(2)
#define ERROR_STRING	"Invalid Input"

char input[MAX];
char output[MAX];
int ftime=0;
void solution(int x, int y, char *s, FILE *out)
{
	if(x==0 && y==0){
		//printf("%s\n",output);
		if(!ftime)
		{
			fprintf(out, "%s", output);
			ftime=1;
		}
		else
			fprintf(out, "\n%s", output);
		return;
	}
	
	if(x>0){
		*s='X';
		solution(x-1,y,s+1,out);
	}

	if(y>0){
		*s='Y';
		solution(x,y-1,s+1,out);
	}
}

int main(int argc, char *argv[])
{
	int x,y, count=0,len=0;
	char buf[MAX], *tmp=NULL;
	FILE *pIn=NULL, *pOut=NULL;
	
	if(argc != 3){
			 printf("Usage: %s (input-file-name) (output-file-name)\n", argv[0]);
			 printf("Please input right filepath!\n");
			 return -1;
	}

	pIn=fopen(argv[1],"r");
	pOut=fopen(argv[2],"w");
	if((!pIn)||(!pOut)) goto _error_out;	
	
	if(!fgets(input,MAX,pIn)) goto _error_out;

	len=strlen(input);
	if((input[len-1]=='\n')||(input[len-1]=='\r')) len-=1;
	if((input[len-1]=='\n')||(input[len-1]=='\r')) len-=1;	
	if(input[0]!='('||input[len-1]!=')') goto _error_out;
	
	memset(buf,0,sizeof(buf));
	sscanf(input,"%*[(]%[^)]",buf);
	tmp=strtok(buf,",");
	while(tmp){
		char *pC=tmp, ttt=0;
		while(*pC){
			if(*pC<'0'||*pC>'9') goto _error_out;
			pC++;
			ttt++;
			if(ttt>MAXBITS) goto _error_out;
		}
		if(count==0) x=atoi(tmp);
		else if(count==1) y=atoi(tmp);
		else goto _error_out;	
		tmp=strtok(NULL,",");
		count++;
	}
	if(x<MINV || x>MAXV || y<MINV || y>MAXV) goto _error_out;
	solution(x-1,y-1,output, pOut);
	fclose(pOut);
	fclose(pIn);
	return 0;
_error_out:
	if(pIn)
		fclose(pIn);
	if(pOut){
		fprintf(pOut, ERROR_STRING);
		fclose(pOut);
	}
	//printf(ERROR_STRING);
	return -1;	
}

