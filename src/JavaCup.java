import java.util.*;

public class JavaCup {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press number1: ");
        int i = scanner.nextInt();
        System.out.println("Press number2: ");
        int j = scanner.nextInt();
        System.out.println("Press number3: ");
        int k = scanner.nextInt();
        temp();
        eval(i, j, k);
    }

    public static void eval(int i, int j, int k)
    {
        if (i * i + j * j == k * k || i * i == j * j + k * k || j * j == i * i + k * k)
        {
            System.out.println("YES");
        }
        else { System.out.println("NO"); }
    }

    public static void temp() {
        List<Integer> list = new LinkedList<>();
        for (int  i = 0; i < 9999; i++) {
            list.addAll(Collections.nCopies(i + 1, i));
            list.addAll(Collections.nCopies(i + 1, 29999 - i));
        }
        for (int i = 9999; i < 20000; i++) {
            list.addAll(Collections.nCopies(10000, i));
        }
    }
}
