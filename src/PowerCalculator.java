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
        if (exponent == 0) {
            return BigInteger.ONE;
        }

        BigInteger result = BigInteger.ONE;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = result.multiply(base);
            }
            base = base.multiply(base);
            exponent /= 2;
        }

        return result;
    }
}