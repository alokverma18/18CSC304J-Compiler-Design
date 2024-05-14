import java.util.*;

public class If_While_Code2 {

    private static int labelCounter = 0;

    public static void main(String[] args) {
        String javaCode = "int x = 5;\n" +
                          "if (x > 0) {\n" +
                          "    System.out.println(\"x is positive\");\n" +
                          "}\n" +
                          "else {\n" +
                          "    System.out.println(\"x is non-positive\");\n" +
                          "}\n" +
                          "while (x > 0) {\n" +
                          "    x--;\n" +
                          "}\n";
        String intermediateCode = generateIntermediateCode(javaCode);
        System.out.println(intermediateCode);
    }

    public static String generateIntermediateCode(String javaCode) {
        StringBuilder intermediateCode = new StringBuilder();
        String[] lines = javaCode.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("if")) {
                intermediateCode.append(generateIfIntermediateCode(line));
            } else if (line.startsWith("while")) {
                intermediateCode.append(generateWhileIntermediateCode(line));
            } else {
                // Handle other statements if necessary
                intermediateCode.append(line).append("\n");
            }
        }

        return intermediateCode.toString();
    }

    public static String generateIfIntermediateCode(String ifStatement) {
        String condition = ifStatement.substring(ifStatement.indexOf("(") + 1, ifStatement.indexOf(")")).trim();
        String trueLabel = getNextLabel();
        String falseLabel = getNextLabel();
        String intermediateCode = String.format("if (%s) goto %s;\n", condition, falseLabel);
        intermediateCode += "  // True branch\n";
        // Here you can add intermediate code for statements inside the if block
        intermediateCode += String.format("goto %s;\n", trueLabel);
        intermediateCode += String.format("%s:\n", falseLabel);
        intermediateCode += "  // False branch\n";
        // Here you can add intermediate code for statements inside the else block
        intermediateCode += String.format("%s:\n", trueLabel);
        return intermediateCode;
    }

    public static String generateWhileIntermediateCode(String whileStatement) {
        String condition = whileStatement.substring(whileStatement.indexOf("(") + 1, whileStatement.indexOf(")")).trim();
        String startLabel = getNextLabel();
        String endLabel = getNextLabel();
        String intermediateCode = String.format("%s:\n", startLabel);
        intermediateCode += String.format("if (!(%s)) goto %s;\n", condition, endLabel);
        // Here you can add intermediate code for statements inside the while loop
        intermediateCode += String.format("goto %s;\n", startLabel);
        intermediateCode += String.format("%s:\n", endLabel);
        return intermediateCode;
    }

    public static String getNextLabel() {
        return "L" + labelCounter++;
    }
}