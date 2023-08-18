import java.math.BigInteger;
import java.util.Scanner;


public class PowerCalculator {
    public static void main(String[] args) {
        BigInteger base = BigInteger.valueOf(2);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the exponent: ");
        int exponent = scanner.nextInt();

        BigInteger result = calculatePower(base, exponent);

        System.out.println(base + "^" + exponent + " = " + result);
    }

    public static BigInteger calculatePower(BigInteger base, int exponent) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < exponent; i++) {
            result = result.multiply(base);
        }

        return result;
    }
}