import java.util.Stack;

public class SyntaxTree {
    public static class Node {
        String value;
        Node left;
        Node right;

        Node(String value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public static Node buildSyntaxTree(String[] postfixExpr) {
        Stack<Node> stack = new Stack<>();

        for (String token : postfixExpr) {
            if (isOperator(token)) {
                Node rightNode = stack.pop();
                Node leftNode = stack.pop();
                Node operatorNode = new Node(token);
                operatorNode.left = leftNode;
                operatorNode.right = rightNode;
                stack.push(operatorNode);
            } else {
                stack.push(new Node(token));
            }
        }
        return stack.pop();
    }

    public static double evaluateSyntaxTree(Node root) {
        if (root == null)
            return 0;

        if (isNumeric(root.value))
            return Double.parseDouble(root.value);

        double leftValue = evaluateSyntaxTree(root.left);
        double rightValue = evaluateSyntaxTree(root.right);

        switch (root.value) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return leftValue / rightValue;
            default:
                throw new IllegalArgumentException("Invalid operator: " + root.value);
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String str) {
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }

    public static void main(String[] args) {
        // Example postfix expression: "5 3 + 2 *"
        String[] postfixExpr = {"5", "3", "*", "2", "+"};

        Node root = buildSyntaxTree(postfixExpr);

        double result = evaluateSyntaxTree(root);
        System.out.println("Result: " + result);
    }
}
