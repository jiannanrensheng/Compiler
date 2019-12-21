#include<stdio.h>
int main(void){
	int a = 1;
	int b = 2;
	int i = 0; 
	int sum = 0;
	char s = 's';
	b /= 2+3;
		
	if (a >= b++) {
		a = 4 + (b + 2) * 3;
	}
	else if(a==2){
		a = 2;
	}
	else {
		i++;
	}
	b = a;
	
	/*
		Hello world
	*/
	
	i = 2;
	i = 2 - 3 * 2;
	for(i = 0;i<=10;i++){
		if(i == 3){
			printf("%d", i);
		}
		else if(i==6&&(a+2)==4){
			scanf("%d", &a);
		}
	}
	
	/*
	for (i = 0; i < 10; i++) {
		b = 1;
	}
	while (i < 10||1==1) {
		sum += i;
		
	}
	*/
}