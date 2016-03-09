import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 2016.03.08.
 */
public class MainTest {

    @Test
    public void testMain() throws Exception {

    }

    @Test
    public void testGetUserInput() throws Exception {

    }

    @Test
    public void testProcessFile() throws Exception {

    }

    @Test
    public void testToMap() throws Exception {

    }

    @Test
    public void testToXML() throws Exception {

    }

    @Test
    public void testFormulaTimeEvent() throws Exception {
        // Decathlon constant values of First event
        double A = 25.4347;
        double B = 18;
        double C = 1.81;

        // P = competitor performance
        double P = 12.61;
        double result = A * Math.pow(B - P, C);

        assertEquals(536, result, 1);
    }

    @Test
    public void testFormulaDistanceEvent() throws Exception {
        // Decathlon constant values of Second event
        double A = 0.14354;
        double B = 220;
        double C = 1.4;

        // P = competitor performance
        double P = 5.00 * 100;
        double result = A * Math.pow(P - B, C);

        assertEquals(382, result, 1);
    }

    @Test
    public void testToSeconds() throws Exception {
        String test = "1.01.";
        String[] time = test.split("\\.");
        int size = time.length;
        int minutes = size > 0 ? Integer.parseInt(time[0]) : 0;
        int seconds = size > 1 ? Integer.parseInt(time[1]) : 0;
        int milliseconds = size > 2 ? Integer.parseInt(time[2].trim()) : 0;
        seconds += minutes * 60 + milliseconds / 1000;

        assertEquals(61, seconds);
    }

    @Test
    public void testSortCompetitors() throws Exception {

    }
}