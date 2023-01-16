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

    private static Acceleration GRAVITY = new Acceleration(9.8, AccelerationUnit.METER_PER_SECOND_2);

    public BasketballFlightSimulation() {
        super("");

        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private static JPanel createChartPanel() {
        String chartTitle = "Basketball flight simulation";
        String categoryAxisLabel = "X";
        String valueAxisLabel = "Y";

        DefaultXYDataset dataset = new DefaultXYDataset();

        // 28
        Angle angle25 = new Angle(25, AngleUnit.DEGREE);
        dataset.addSeries("25 deg", createModelAndOutput(angle25));

        // 34
        Angle angle35 = new Angle(35, AngleUnit.DEGREE);
        dataset.addSeries("35 deg", createModelAndOutput(angle35));

        // 40
        Angle angle45 = new Angle(45, AngleUnit.DEGREE);
        dataset.addSeries("45 deg", createModelAndOutput(angle45));

        // 44
        Angle angle55 = new Angle(55, AngleUnit.DEGREE);
        dataset.addSeries("55 deg", createModelAndOutput(angle55));

        // 48
        Angle angle65 = new Angle(65, AngleUnit.DEGREE);
        dataset.addSeries("65 deg", createModelAndOutput(angle65));

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, categoryAxisLabel, valueAxisLabel, dataset);

        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BasketballFlightSimulation().setVisible(true);
            }
        });
    }

    private static double[][] createModelAndOutput(Angle angleInDeg) {
        Speed throwingSpeed = new Speed(13.9, SpeedUnit.METER_PER_SECOND);

        Length distance = new Length (8, LengthUnit.METER);
        Length basketHeight = new Length (3.05, LengthUnit.METER);

        Angle angleInRad = new Angle(angleInDeg.getInUnit(AngleUnit.RADIAN), AngleUnit.RADIAN);

        Time startTime = new Time(0, TimeUnit.BASE_SECOND);
        Time deltaTime = new Time(30, TimeUnit.BASE_MILLISECOND);

        Position x0 = new Position(0, PositionUnit.METER);
        Position y0 = new Position(1.6, PositionUnit.METER);

        Speed v0 = new Speed(throwingSpeed.doubleValue(), SpeedUnit.METER_PER_SECOND);
        Speed vX0 = new Speed(v0.doubleValue() * Math.cos(angleInRad.doubleValue()), SpeedUnit.METER_PER_SECOND);
        Speed vY0 = new Speed(v0.doubleValue() * Math.sin(angleInRad.doubleValue()), SpeedUnit.METER_PER_SECOND);

        Speed vX;
        Speed vY;

        Length distanceMargin = new Length(10, LengthUnit.CENTIMETER);

        Position x = new Position(x0.doubleValue(), PositionUnit.METER);
        Position y = new Position(y0.doubleValue(), PositionUnit.METER);

        int numberOfDataPoints = 100;
        double[][] data = new double[2][numberOfDataPoints];

        Time currentTime;
        int timeCounter = 0;
        while (y.doubleValue() > 0) {

            currentTime = new Time(startTime.doubleValue() + (timeCounter * deltaTime.doubleValue()), TimeUnit.BASE_SECOND);

            vX = vX0;
            vY = new Speed((vY0.doubleValue() - (deltaTime.doubleValue() * timeCounter) * GRAVITY.doubleValue()), SpeedUnit.METER_PER_SECOND);
            x = new Position(x0.doubleValue() + vX.doubleValue() * currentTime.doubleValue(), PositionUnit.METER);
            y = new Position(y0.doubleValue() + vY.doubleValue() * currentTime.doubleValue(), PositionUnit.METER);

            if (Math.abs(distance.doubleValue() - x.doubleValue()) < distanceMargin.doubleValue() &&
                    Math.abs(basketHeight.doubleValue() - y.doubleValue()) < distanceMargin.doubleValue() ) {

                // System.out.println(currentTime + ": [x,y] = [" + x + ", " + y + "] - SCORE..!");
            } else {
                // System.out.println(currentTime + ": [x,y] = [" + x + ", " + y + "]");
            }
            // System.out.println("[vX,vY] = [" + vX + ", " + vY + "]");

            data[0][timeCounter] = x.doubleValue();
            data[1][timeCounter] = y.doubleValue();

            timeCounter++;
            // System.out.println("timeCounter: " + timeCounter);
        }

        int length = evaluateLength(data);
        double[][] truncatedArray = truncateArray(data, length);

        return truncatedArray;
    }

    /**
     * Copy all the values of the old array to a new, shorter array
     * @param data the old array
     * @param newLength
     * @return the new array with the old data up to the newLength
     */
    public static double[][] truncateArray(double[][] data, int newLength) {
        double[][] target = new double[2][newLength];
        for(int i=0; i < data.length ; i++) {
            for (int j = 0; j < newLength; j++) {
                // System.out.println("[" + i + ", " + j + "]");
                target[i][j] = data[i][j];
            }
        }
        return target;
    }

    /**
     * Evaluate how long the array should actually be by determining when the contents is [0,0]
     * @param data the array to be evaluated
     * @return the length the array should actually be
     */
    private static int evaluateLength(double[][] data) {
        int length = 0;
        for (int i = 0; i < data[0].length ; i++) {
            // System.out.println("[" + data[0][i] + ", " + data[1][i] + "]");
            if (data[0][i] == 0 && data[1][i] == 0) {
                length = i;
                break;
            }
        }
        return length;
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
    }
}
