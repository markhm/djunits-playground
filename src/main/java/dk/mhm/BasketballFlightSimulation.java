
package dk.mhm;

import org.djunits.unit.*;
import org.djunits.value.vdouble.scalar.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;

/**
 * Example of a basketball that is thrown by a player from 1.6 meters high under a 45-degree angle with a certain speed.
 * At which speed will it go through the hoop at 8 meters distance, which is 3.05 m high...?
 */
public class BasketballFlightSimulation extends JFrame {

    // Defines the gravity's acceleration constant (= how quickly does something fall on earth)
    private static Acceleration GRAVITY = new Acceleration(9.8, AccelerationUnit.METER_PER_SECOND_2);

    public BasketballFlightSimulation() {
        // Calls the constructor of the super-class with an empty title (""). TODO Note that a super constructor is called
        super("");

        // Creates a object called chartPanel using the static createChartPanel method below. TODO Note that it is called object and that the object is created using a static method
        JPanel chartPanel = createChartPanel();

        // Adds the chartPanel to the JFrame, see the Javadoc for JFrame here:
        // https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html. Note that you _don't_ see the add(.., ..) method, but that
        // you need to go up to one of its super-classes (java.awt.Container) to see the method:
        // See this one: https://docs.oracle.com/javase/8/docs/api/java/awt/Container.html
        add(chartPanel, BorderLayout.CENTER);

        // sets width and height of the chart
        setSize(1024, 768);

        // Tells JFrame what to do when the user clicks the close button.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Specifies where the window should be relative to the specified component. See here:
        // https://docs.oracle.com/javase/8/docs/api/java/awt/Window.html#setLocationRelativeTo-java.awt.Component- and read
        // "If the component is null, or the GraphicsConfiguration associated with this component is null,
        // the window is placed in the center of the screen."
        setLocationRelativeTo(null);
    }

    private static JPanel createChartPanel() {
        // Creates a variable for the chart title
        String chartTitle = "Basketball flight simulation";

        // Creates a variable for the x-axis label
        String categoryAxisLabel = "X";

        // Creates a variable for the y-axis label
        String valueAxisLabel = "Y";

        DefaultXYDataset dataset = new DefaultXYDataset(); // Creates a new dataset.

        // creates a variable equal to 25 degrees
        Angle angle25 = new Angle(25, AngleUnit.DEGREE);

        // calls the method with the angle and adds it to the dataset with a label
        dataset.addSeries("25 deg", createModelAndOutput(angle25));

        // creates a variable equal to 35 degrees
        Angle angle35 = new Angle(35, AngleUnit.DEGREE);
        dataset.addSeries("35 deg", createModelAndOutput(angle35));

        Angle angle45 = new Angle(45, AngleUnit.DEGREE);
        dataset.addSeries("45 deg", createModelAndOutput(angle45));

        Angle angle55 = new Angle(55, AngleUnit.DEGREE);
        dataset.addSeries("55 deg", createModelAndOutput(angle55));

        Angle angle65 = new Angle(65, AngleUnit.DEGREE);
        dataset.addSeries("65 deg", createModelAndOutput(angle65));

        // Creates an object called chart which creates a chart TODO Note it is called object
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, categoryAxisLabel, valueAxisLabel, dataset);

        return new ChartPanel(chart); // returns the chart
    }

    /**
     * Public static void main is where a Java application starts.
     *
     * @param args
     */
    public static void main(String[] args) {

        // TODO If you want to change something in a UI component, you need to schedule this change, see:
        // https://www.tutorialspoint.com/what-is-the-importance-of-swingutilities-class-in-java

        SwingUtilities.invokeLater(

            // TODO This is difficult: run() is the interface that the invokeLater() method needs. Instead of
            // passing a class that implements this interface, here only the method is implemented directly.
            new Runnable() {
            @Override
            public void run() {
                new BasketballFlightSimulation().setVisible(true);  // Makes the window visible
            }
        });
    }

    public static double[][] createModelAndOutput(Angle angleInDeg) {
        // Creates variable equal to the speed of the ball (13.9)
        Speed throwingSpeed = new Speed(13.9, SpeedUnit.METER_PER_SECOND);

        // Creates a Length object equal to the distance from the player to the player.
        Length distance = new Length (8, LengthUnit.METER);

        // Creates a Length object equal to the height of the basket.
        Length basketHeight = new Length (3.05, LengthUnit.METER);

        // Creates a new object equal to the angle in radians
        Angle angleInRad = new Angle(angleInDeg.getInUnit(AngleUnit.RADIAN), AngleUnit.RADIAN);

        // Creates a Time object called startTime equal to 0 seconds.
        Time startTime = new Time(0, TimeUnit.BASE_SECOND);

        // Creates a Time object called deltaTime equal to 30 milliseconds.
        Time deltaTime = new Time(30, TimeUnit.BASE_MILLISECOND);

        // Creates a Position object x0 equal to the starting x coordinate
        Position x0 = new Position(0, PositionUnit.METER);

        // Creates a Position object y0 equal to the starting y coordinate
        Position y0 = new Position(1.6, PositionUnit.METER);

        // Creates a variable v0 equal to the throwingSpeed variable as a double value.
        Speed v0 = new Speed(throwingSpeed.doubleValue(), SpeedUnit.METER_PER_SECOND);

        // See here for the mathematical explanation of the decomposition of the vector: https://math.stackexchange.com/a/2269988
        Speed vX0 = new Speed(v0.doubleValue() * Math.cos(angleInRad.doubleValue()), SpeedUnit.METER_PER_SECOND);
        Speed vY0 = new Speed(v0.doubleValue() * Math.sin(angleInRad.doubleValue()), SpeedUnit.METER_PER_SECOND);

        Speed vX;
        Speed vY;

        // Defines the margin of distance to determine whether the ball is close enough to the basket.
        Length distanceMargin = new Length(10, LengthUnit.CENTIMETER);

        // Creates Position object x equal to the x0 variable but as a double value.
        Position x = new Position(x0.doubleValue(), PositionUnit.METER);
        // Creates Position object y equal to the y0 variable but as a double value.
        Position y = new Position(y0.doubleValue(), PositionUnit.METER);

        // creates an integer variable (!) equal to a large number of data points that will fit the
        // data for all the situations
        int numberOfDataPoints = 100;

        // Creates a 2 dimensional array with data of type double where the first dimension has 2 spaces (for x and y)
        // and the second dimension has spaces equal to the number of data points.
        double[][] data = new double[2][numberOfDataPoints];

        // Creates undefined Time object called currentTime.
        Time currentTime;

        // Creates a time counter integer variable equal to 0.
        int timeCounter = 0;
        while (y.doubleValue() > 0) {

            // Creates variable currentTime equal to the current time.
            currentTime = new Time(startTime.doubleValue() + (timeCounter * deltaTime.doubleValue()), TimeUnit.BASE_SECOND);

            // Sets the variable vX as equal to the variable vX0
            vX = vX0;

            // The speed changes based on the time. The time progresses based in small steps (deltaTime), multiplied by the timeCounter
            vY = new Speed((vY0.doubleValue() - (deltaTime.doubleValue() * timeCounter) * GRAVITY.doubleValue()), SpeedUnit.METER_PER_SECOND);

            // Calculates the new x-position based on the xSpeed (vX) times the currentTime
            x = new Position(x0.doubleValue() + vX.doubleValue() * currentTime.doubleValue(), PositionUnit.METER);
            // Calculates the new y-position based on the xSpeed (yX) times the currentTime
            y = new Position(y0.doubleValue() + vY.doubleValue() * currentTime.doubleValue(), PositionUnit.METER);

            if (Math.abs(distance.doubleValue() - x.doubleValue()) < distanceMargin.doubleValue() &&
                    Math.abs(basketHeight.doubleValue() - y.doubleValue()) < distanceMargin.doubleValue() ) {
                // System.out.println(currentTime + ": [x,y] = [" + x + ", " + y + "] - SCORE..!");
            } else {
                // System.out.println(currentTime + ": [x,y] = [" + x + ", " + y + "]");
            }
            // System.out.println("[vX,vY] = [" + vX + ", " + vY + "]");

            // Sets the value of x for that time in the data array
            data[0][timeCounter] = x.doubleValue();
            // Sets the value of y for that time in the data array
            data[1][timeCounter] = y.doubleValue();

            // Adds 1 to timeCounter
            timeCounter++;
            // System.out.println("timeCounter: " + timeCounter);
        }

        // Finds out how long the array should be by checking when the array both values from
        // both dimensions of the array are equal to 0.
        int length = evaluateLength(data);

        // Creates array of data type double equal to the data array but only up to the length AND COPIES THE DATA.
        double[][] truncatedArray = truncateArray(data, length);

        return truncatedArray; // Returns the final array of data.
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
    private static int evaluateLength(double[][] data) {
        int length = 0; // Creates integer variable set to 0.
        for (int i = 0; i < data[0].length ; i++) {
            // System.out.println("[" + data[0][i] + ", " + data[1][i] + "]");
            if (data[0][i] == 0 && data[1][i] == 0) {
                length = i;
                break;
            }
        }
        // loops through the data array and finds the iteration where the correspondent values in
        // data[0] and data[1] are both equal to 0 and sets length equal to the iteration number.
        return length; // Returns the length
    }
}
