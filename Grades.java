import java.util.Scanner;
public class Grades {
    public static void main(String args[]) {
        // Array to store subject names
        String[] subjects = {"Complier Design", "AI", "OPEN-ELECTIVE", "DBMS", "PE-B", "PE-D", "IAF", "DBMS-LAB", "CD-LAB", "AI-LAB"};
        int marks[] = new int[10];
        int i;
        float total=0, avg;
        Scanner scanner = new Scanner(System.in);
        for(i=0; i<10; i++) {
            System.out.print("Enter Marks of " + subjects[i] + ": ");
            marks[i] = scanner.nextInt();
            total = total + marks[i];
        }
        scanner.close();
        avg = total/10;        
        System.out.println("Total : " + total);
        System.out.println("Average : " + avg);
        System.out.print("The student Grade is: ");
        if(avg>90)
            System.out.print("O");
        else if(avg>80)
            System.out.print("A+");
        else if(avg>70)
           System.out.print("A");
        else if(avg>60)
            System.out.print("B+");
        else if(avg>50)
            System.out.print("B");
        else
            System.out.print("F");
    }
}
