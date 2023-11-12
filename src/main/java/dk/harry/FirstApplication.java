package dk.harry;

public class FirstApplication {

    public static void main(String[] args) {
        // here we start

        System.out.println("Hello world");
        System.out.println();

        String name = "Harry";
        System.out.println("Hello " + name);

        System.out.println();

        countToTen();
    }

    public static void countToTen() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("number: "+ i);

            if (i % 2 == 0) {
                System.out.println(i + " is an even number");
            } else {
                System.out.println(i + " is an odd number");
            }
        }
    }
}
