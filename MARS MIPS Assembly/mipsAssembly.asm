.data
num1: .word 10
num2: .word 6
.text
main:
    # load num1 into $a0
    lw $a0, num1
    # load num2 into $a1
    lw $a1, num2
    # add num1 and num2
    add $t0, $a0, $a1      # Store the result in $t0
    # print result
    move $a0, $t0          # Move the result from $t0 to $a0 for printing
    
    li $v0, 1              # syscall code for print_int
    syscall
    
    # exit program
    li $v0, 10             # syscall code for exit
    syscall