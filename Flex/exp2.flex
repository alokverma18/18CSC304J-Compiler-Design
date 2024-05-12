%{
%}
%%
[0-9] {printf("Numerics in C");}
[a-z] {printf("Lowercase Alphabet in C");}
[A-Z] {printf("Uppercase Alphabet in C");}
%%
void main()
{
printf("Enter the String : ");yylex();
}
int yywrap(){}