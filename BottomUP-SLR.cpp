#include <stdio.h>
#include <string.h>
#include <stdbool.h>

struct ProductionRule
{
    char left[10];
    char right[10];
};

int main()
{
    char input[20], stack[50], temp[50], ch[2], *token1, *token2, *substring;
    int i, j, stack_length, substring_length, stack_top, rule_count = 0;
    struct ProductionRule rules[10];

    stack[0] = '\0';

    printf("\nEnter the number of production rules: ");
    scanf("%d", &rule_count);

    printf("\nEnter the production rules (in the form 'left->right'): \n");
    for (i = 0; i < rule_count; i++)
    {
        scanf("%s", temp);
        token1 = strtok(temp, "->");
        token2 = strtok(NULL, "->");
        strcpy(rules[i].left, token1);
        strcpy(rules[i].right, token2);
    }

    printf("\nEnter the input string: ");
    scanf("%s", input);
    
    printf("\nStack \t Input \t Action\n");
    i = 0;
    
    bool flag = true;
    
    while (1)
    {
        if (i < strlen(input))
        {
            ch[0] = input[i];
            ch[1] = '\0';
            i++;
            strcat(stack, ch);
            printf("%s\t", stack);
            for (int k = i; k < strlen(input); k++)
            {
                printf("%c", input[k]);
            }
            printf("\tShift %s\n", ch);
        }
        
        for (j = 0; j < rule_count; j++)
        {       
            substring = strstr(stack, rules[j].right);
            if (substring != NULL)
            {
                stack_length = strlen(stack);
                substring_length = strlen(substring);
                stack_top = stack_length - substring_length;
                stack[stack_top] = '\0';
                strcat(stack, rules[j].left);
                printf("%s\t", stack);
                for (int k = i; k < strlen(input); k++)
                {
                    printf("%c", input[k]);
                }
                printf("\tReduce %s->%s\n", rules[j].left, rules[j].right);
                
                if (strcmp(stack, rules[0].left) == 0 && i == strlen(input))
                {
                    printf("\nAccepted");
                    flag = false;
                    break;
                } else{
                    j = -1; 
                }
            }
        }


        if (i == strlen(input))
        {
            if(flag){
                printf("\nNot Accepted");
            }
            break;
        }
    }

    return 0;
}
