package dk.mhm.maths;

import dk.mhm.ArrayUtils;
import org.djunits.unit.PositionUnit;
import org.djunits.unit.TimeUnit;
import org.djunits.value.vdouble.scalar.Position;
import org.djunits.value.vdouble.scalar.Time;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;

public class SinePlotExample extends JFrame {

    public SinePlotExample() {
        super("");

        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(1024, 768);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    new SinePlotExample().setVisible(true);  // Makes the window visible
                }
            });
    }

    private static JPanel createChartPanel() {
        DefaultXYDataset dataset = createSineDataset(); // Creates a new dataset.

        JFreeChart chart = ChartFactory.createXYLineChart("Sine dataset", "X", "Y", dataset);

        return new ChartPanel(chart); // returns the chart
    }

    private static DefaultXYDataset createSineDataset() {

        Time startTime = new Time(0, TimeUnit.BASE_SECOND);

        Time maxDuration = new Time(4 * Math.PI, TimeUnit.BASE_SECOND);
        int numberOfDataPoints = 100;

        Time deltaTime = new Time((maxDuration.doubleValue() / numberOfDataPoints), TimeUnit.BASE_SECOND);

        System.out.println("deltaTime: " + deltaTime);
        System.out.println("maxDuration: " + maxDuration);
        Time currentTime = startTime;

        Position x;
        Position y;

        double[][] data = new double[2][numberOfDataPoints];

        int timeCounter = 0;

        while (currentTime.doubleValue() < maxDuration.doubleValue()) {
            x = new Position(Math.sin(currentTime.doubleValue()), PositionUnit.METER);
            y = new Position(Math.cos(currentTime.doubleValue()), PositionUnit.METER);

            // Position coordinateToDisplay = x;
            // System.out.println(currentTime + " - [" + currentTime.doubleValue() + ", " + coordinateToDisplay + "]");

            System.out.println("[" + currentTime.doubleValue() + ", " + y.doubleValue() + "]");

            data[0][timeCounter] = currentTime.doubleValue();
            data[1][timeCounter] = y.doubleValue();

            timeCounter++;
            currentTime = new Time((startTime.doubleValue() + timeCounter * deltaTime.doubleValue()), TimeUnit.BASE_SECOND);
        }

        int arrayLength = ArrayUtils.evaluateLength(data);
        System.out.println("arrayLength: " + arrayLength);
        // Creates array of data type double equal to the data array but only up to the length AND COPIES THE DATA.
        double[][] truncatedArray = ArrayUtils.truncateArray(data, arrayLength);

        DefaultXYDataset dataset = new DefaultXYDataset(); // Creates a new dataset.
        dataset.addSeries("sine", truncatedArray);

        // printDataset(data);
        return dataset;
    }
}
