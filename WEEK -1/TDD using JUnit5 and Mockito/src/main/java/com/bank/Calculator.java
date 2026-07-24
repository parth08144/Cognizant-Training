public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return (double) a / b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println("===== Calculator Demo =====");
        System.out.println("Addition:       10 + 20 = " + calc.add(10, 20));
        System.out.println("Subtraction:    20 - 5  = " + calc.subtract(20, 5));
        System.out.println("Multiplication: 4 * 5   = " + calc.multiply(4, 5));
        System.out.println("Division:       10 / 3  = " + calc.divide(10, 3));

        try {
            System.out.println("Division:       10 / 0  = " + calc.divide(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Division:       10 / 0  = Error - " + e.getMessage());
        }
    }
}
