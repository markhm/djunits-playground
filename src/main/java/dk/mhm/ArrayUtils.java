package dk.mhm;

public class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * Copy all the values of the old array to a new, shorter array
     * @param data the old array
     * @param newLength
     * @return the new array with the old data up to the newLength
     */
    public static double[][] truncateArray(double[][] data, int newLength) {
        // Creates an array of data type double where the first dimension has a length of 2 and the second has a
        // length equal to the newLength parameter.
        double[][] target = new double[2][newLength];

        // Loop through the data array and sets the value for target in that
        // position to the value of the data array in that position.
        for(int i=0; i < data.length ; i++) {
            for (int j = 0; j < newLength; j++) {
                // System.out.println("[" + i + ", " + j + "]");
                target[i][j] = data[i][j];
            }
        }
        // returns the shorter array
        return target;
    }

    /**
     * Evaluate how long the array should actually be by determining when the contents is [0,0]
     * @param data the array to be evaluated
     * @return the length the array should actually be
     */
    public static int evaluateLength(double[][] data) {
        int length = 0; // Creates integer variable set to 0.
        for (int i = 0; i < data[0].length ; i++) {
            // System.out.println("[" + data[0][i] + ", " + data[1][i] + "]");
            if (data[0][i] == 0 && data[1][i] == 0) {
                length = i;
                break;
            }
        }
        if (length == 0) {
            length = data[0].length;
        }
        // loops through the data array and finds the iteration where the correspondent values in
        // data[0] and data[1] are both equal to 0 and sets length equal to the iteration number.
        return length; // Returns the length
    }

    /**
     * Prints the dataset as [x,y] coordinates.
     * @param data
     */
    public static void printDataset(double[][] data) {
        for(int i=0; i < data.length ; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.println("[" + data[0][j] + ", " + data[1][j] + "]");
            }
        }
    }
}
