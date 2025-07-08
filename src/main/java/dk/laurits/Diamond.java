package dk.laurits;

public class Diamond {

    public static void main(String[] args) {

        String name = "Laurits";

        System.out.println();
        System.out.println();
        System.out.println("Hallo, ik heet " + name + ".");
        System.out.println();
        System.out.println();

        printDiamond(8);
    }

    private static void printDiamond(int size) {
        // upper half
        for (int i=0; i<=size; i++) {
            for (int j=i; j<=i+size; j++) {
                System.out.print(" ");
            }
            for (int j=0; j<=i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        // lower half
        for (int i=size; i>=0; i--) {
            for (int j=0; j<=size; j++) {
                System.out.print(" ");
            }
            for (int j=0; j<=i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
