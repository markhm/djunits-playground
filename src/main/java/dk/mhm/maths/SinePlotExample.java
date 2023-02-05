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
        int timeCounter = 0;
        int numberOfDataPoints = 100;
        double[][] data = new double[2][numberOfDataPoints];


        // deltaTime is equal to the maxDuration divided by the numberOfDataPoints
        Time deltaTime = new Time((maxDuration.doubleValue() / numberOfDataPoints), TimeUnit.BASE_SECOND);

        Time currentTime = startTime;

        Position x;
        Position y;

        while (currentTime.doubleValue() < maxDuration.doubleValue()) {
            x = new Position(Math.sin(currentTime.doubleValue()), PositionUnit.METER);
            y = new Position(Math.cos(currentTime.doubleValue()), PositionUnit.METER);

            // System.out.println("[" + currentTime.doubleValue() + ", " + y.doubleValue() + "]");

            data[0][timeCounter] = currentTime.doubleValue();
            data[1][timeCounter] = y.doubleValue();

            timeCounter++;
            currentTime = new Time((startTime.doubleValue() + timeCounter * deltaTime.doubleValue()), TimeUnit.BASE_SECOND);
        }

        int arrayLength = ArrayUtils.evaluateLength(data);
        // Creates array of data type double equal to the data array but only up to the length and copies the data.
        double[][] truncatedArray = ArrayUtils.truncateArray(data, arrayLength);

        DefaultXYDataset dataset = new DefaultXYDataset(); // Creates a new dataset.
        dataset.addSeries("sine", truncatedArray);

        ArrayUtils.printDataset(data);
        return dataset;
    }
}
