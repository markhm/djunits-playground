
package dk.mhm;

import org.djunits.unit.*;
import org.djunits.value.vdouble.scalar.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Example of a basketball that is thrown by a player from 1.6 meters high under a 45-degree angle with a certain speed.
 * At which speed will it go through the hoop at 8 meters distance, which is 3.05 m high...?
 */
public class BasketballFlightSimulation extends JFrame {

    private static Acceleration GRAVITY = new Acceleration(9.8, AccelerationUnit.METER_PER_SECOND_2); // Defines the speed 

    public BasketballFlightSimulation() {
        super(""); // Sets the title of the window.

        JPanel chartPanel = createChartPanel(); // Creates a variable called chartPanel using the createChartPanel function below.
        add(chartPanel, BorderLayout.CENTER); // TODO

        setSize(1024, 768); // sets width and height of the chart
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tells JFrame what to do when the user clicks the close button.
        setLocationRelativeTo(null); // Specifies where the window should be relative to the specified component. TODO
    }

    private static JPanel createChartPanel() {
        String chartTitle = "Basketball flight simulation"; // Creates a variable for the chart title
        String categoryAxisLabel = "X"; // Creates a variable for the x axis label
        String valueAxisLabel = "Y"; // Creates a variable for the y axis label

        DefaultXYDataset dataset = new DefaultXYDataset(); // Creates a new dataset.

        // 28
        Angle angle25 = new Angle(25, AngleUnit.DEGREE); // creates a variable equal to 25 degrees
        dataset.addSeries("25 deg", createModelAndOutput(angle25)); // calls the function with the angle and adds it to the dataset with a label

        // 34
        Angle angle35 = new Angle(35, AngleUnit.DEGREE); // creates a variable equal to 35 degrees
        dataset.addSeries("35 deg", createModelAndOutput(angle35)); // calls the function with the angle and adds it to the dataset with a label

        // 40
        Angle angle45 = new Angle(45, AngleUnit.DEGREE); // creates a variable equal to 45 degrees
        dataset.addSeries("45 deg", createModelAndOutput(angle45)); // calls the function with the angle and adds it to the dataset with a label

        // 44
        Angle angle55 = new Angle(55, AngleUnit.DEGREE); // creates a variable equal to 55 degrees
        dataset.addSeries("55 deg", createModelAndOutput(angle55)); // calls the function with the angle and adds it to the dataset with a label

        Angle angle65 = new Angle(65, AngleUnit.DEGREE); // creates a variable equal to 65 degrees
        dataset.addSeries("65 deg", createModelAndOutput(angle65)); // calls the function with the angle and adds it to the dataset with a label

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, categoryAxisLabel, valueAxisLabel, dataset); // Creates variable called chart which creates a chart

        return new ChartPanel(chart); // returns the chart
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            // TODO
            new Runnable() {
            @Override // TODO
            public void run() {
                new BasketballFlightSimulation().setVisible(true);  // Makes the window visible TODO.
            }
        });
    }

    private static double[][] createModelAndOutput(Angle angleInDeg) {
        Speed throwingSpeed = new Speed(13.9, SpeedUnit.METER_PER_SECOND); // Creates variable equal to the speed of the ball (13.9)

        Length distance = new Length (8, LengthUnit.METER); // Creates a variable equal to the distance from the player to the player.
        Length basketHeight = new Length (3.05, LengthUnit.METER); // Creates a variable equal to the height of the basket.

        Angle angleInRad = new Angle(angleInDeg.getInUnit(AngleUnit.RADIAN), AngleUnit.RADIAN); // Creates a variable equal to the angle in radian

        Time startTime = new Time(0, TimeUnit.BASE_SECOND); // Creates a startTime variable equal to 0 seconds.
        Time deltaTime = new Time(30, TimeUnit.BASE_MILLISECOND); // Creates a deltaTime variable equal to 30 milliseconds.

        Position x0 = new Position(0, PositionUnit.METER); // Creates a variable equal to the starting x coordinate
        Position y0 = new Position(1.6, PositionUnit.METER); // Creates a variable equal to the starting y coordinate

        Speed v0 = new Speed(throwingSpeed.doubleValue(), SpeedUnit.METER_PER_SECOND); // Creates a variable v0 equal to the throwingSpeed variable as a double value.
        Speed vX0 = new Speed(v0.doubleValue() * Math.cos(angleInRad.doubleValue()), SpeedUnit.METER_PER_SECOND); // TODO
        Speed vY0 = new Speed(v0.doubleValue() * Math.sin(angleInRad.doubleValue()), SpeedUnit.METER_PER_SECOND); // TODO

        Speed vX; // Creates undefined variable
        Speed vY; // Creates undefined variable

        Length distanceMargin = new Length(10, LengthUnit.CENTIMETER); // Defines the margin of distance from the basket.

        Position x = new Position(x0.doubleValue(), PositionUnit.METER); // Creates variable x equal to the x0 variable but as a double value.
        Position y = new Position(y0.doubleValue(), PositionUnit.METER); // Creates variable y equal to the y0 variable but as a double value.

        int numberOfDataPoints = 100; // creates variable equal to the number of data points
        double[][] data = new double[2][numberOfDataPoints]; // Creates a 2 dimensional array with data of type double where the first dimension has 2 spaces and the second dimension has spaces equal to the number of data points.

        Time currentTime; // Creates undefined time variable.
        int timeCounter = 0; // Creates a time counter inter variable equal to 0.
        while (y.doubleValue() > 0) {

            currentTime = new Time(startTime.doubleValue() + (timeCounter * deltaTime.doubleValue()), TimeUnit.BASE_SECOND);  // Creates variable currentTime equal to the current time.

            vX = vX0; // Sets the variable vX as equal to the variable vX0
            vY = new Speed((vY0.doubleValue() - (deltaTime.doubleValue() * timeCounter) * GRAVITY.doubleValue()), SpeedUnit.METER_PER_SECOND); // TODO
            x = new Position(x0.doubleValue() + vX.doubleValue() * currentTime.doubleValue(), PositionUnit.METER); // Changes the variable x to the current position TODO
            y = new Position(y0.doubleValue() + vY.doubleValue() * currentTime.doubleValue(), PositionUnit.METER); // Changes the variable y to the current position TODO

            if (Math.abs(distance.doubleValue() - x.doubleValue()) < distanceMargin.doubleValue() &&
                    Math.abs(basketHeight.doubleValue() - y.doubleValue()) < distanceMargin.doubleValue() ) {
                // System.out.println(currentTime + ": [x,y] = [" + x + ", " + y + "] - SCORE..!");
            } else {
                // System.out.println(currentTime + ": [x,y] = [" + x + ", " + y + "]");
            }
            // System.out.println("[vX,vY] = [" + vX + ", " + vY + "]");

            data[0][timeCounter] = x.doubleValue(); // Sets the value of x for that time in the data array 
            data[1][timeCounter] = y.doubleValue(); // Sets the value of y for that time in the data array

            timeCounter++; // Adds 1 to timecounter
            // System.out.println("timeCounter: " + timeCounter);
        }

        int length = evaluateLength(data); // Finds out how long the array should be by checking when the array both values from both dimensions of the array are equal to 0.
        double[][] truncatedArray = truncateArray(data, length); // Creates array of data type double equal to the data array but only up to the length.

        return truncatedArray; // Returns the final array of data.
    }

    /**
     * Copy all the values of the old array to a new, shorter array
     * @param data the old array
     * @param newLength
     * @return the new array with the old data up to the newLength
     */
    public static double[][] truncateArray(double[][] data, int newLength) {
        double[][] target = new double[2][newLength]; // Creates a array of data type double where the first dimension has a length of 2 and the second has a length equal to the newLength parameter.
        for(int i=0; i < data.length ; i++) {
            for (int j = 0; j < newLength; j++) {
                // System.out.println("[" + i + ", " + j + "]");
                target[i][j] = data[i][j];
            }
        }
        // Loops through the data array and sets the value for target in that position to the value of the data array in that position. 
        return target; // returns the shorter array
    }

    /**
     * Evaluate how long the array should actually be by determining when the contents is [0,0]
     * @param data the array to be evaluated
     * @return the length the array should actually be
     */
    private static int evaluateLength(double[][] data) {
        int length = 0; // Creates integer variable set to 0.
        for (int i = 0; i < data[0].length ; i++) {
            // System.out.println("[" + data[0][i] + ", " + data[1][i] + "]");
            if (data[0][i] == 0 && data[1][i] == 0) {
                length = i;
                break;
            }
        }
        // loops throught the data array and finds the iteration where the correspondent values in data[0] and data[1] are both equal to 0 and sets length equal to the iteration number.
        return length; // Returns the length
    }

    private static XYDataset createDataset() {

        DefaultXYDataset dataset = new DefaultXYDataset();

        String series1 = "Curve";

        double[][] data = new double[2][10];

        for (int i = 0; i < 10; i++) {
            data[0][i] = i;
            data[1][i] = Math.random() * 100;
        }

        dataset.addSeries(series1, data);

        return dataset;

        // TODO
    }

}