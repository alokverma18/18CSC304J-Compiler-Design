public class If_While_Code {
    
    // Function to generate code for the if statement
    public static String generateIfCode(String condition, String action) {
        String code = "if (" + condition + ") {\n";
        code += "\t" + action + "\n";
        code += "}";
        return code;
    }

    // Function to generate code for the while loop
    public static String generateWhileCode(String condition, String action) {
        String code = "while (" + condition + ") {\n";
        code += "\t" + action + "\n";
        code += "}";
        return code;
    }

    public static void main(String[] args) {
        
        // Example input: if (x > 8) { y = x * 2; }
        String if_condition = "x > 8";
        String if_action = "y = x * 2;";
        
        String ifCode = generateIfCode(if_condition, if_action);
        System.out.println("Generated code for If statement:\n" + ifCode);
        
        // Example input: while (i < 10) { System.out.println(i); i++; }
        String while_condition = "i < 10";
        String while_action = "System.out.println(i); i++;";
        
        String whileCode = generateWhileCode(while_condition, while_action);
        System.out.println("Generated code for While loop:\n" + whileCode);
    }
}
