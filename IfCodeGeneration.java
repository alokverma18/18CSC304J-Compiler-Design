import java.util.*;
public class IfCodeGeneration {
    public static void main(String[] args) {
    
        // Example input: if (x > 5) { y = x * 2; }
        String condition = "x > 5";
        String action = "y = x * 2;";
 
        // Generate code for the if statement   
        String generatedCode = generateIfCode(condition, action);
    
        System.out.println("Generated code:");
 
        System.out.println(generatedCode);
    }

    public static String generateIfCode(String condition, String action) {

        StringBuilder codeBuilder = new StringBuilder();

        // Add the if statement
        codeBuilder.append("if (").append(condition).append(") {\n");

        // Add the action inside the if block
        codeBuilder.append("\t").append(action).append("\n");

        // Close the if block
        codeBuilder.append("}");

        return codeBuilder.toString();
    }
}
