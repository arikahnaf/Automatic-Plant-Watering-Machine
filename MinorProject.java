package eecs1021;

import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.I2CDevice;
import java.util.Timer;
import java.io.IOException;
import edu.princeton.cs.introcs.StdDraw;
import java.util.HashMap;

public class MinorProject
{
    static final byte I2C0 = 0x3c;
    private static Integer clock;

    public static void main(String[] args)
            throws IOException
    {
        String myPort = "COM3";
        IODevice myGroveBoard = new FirmataDevice(myPort);

        try
        {
            myGroveBoard.start();
            System.out.println("Board is operating.");
            myGroveBoard.ensureInitializationIsDone();
        }
        catch(Exception ex)
        {
            System.out.println("Board Connection Failed.");
        }
        finally
        {
            var mySensor = myGroveBoard.getPin(15);
            var myPump = myGroveBoard.getPin(2);
            myPump.setMode(Pin.Mode.OUTPUT);

            I2CDevice i2cObject = myGroveBoard.getI2CDevice((byte) 0x3c);
            SSD1306 MoistureDisplay = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
            MoistureDisplay.init();
            var T = new Timer();
            var Task = new MinorProjectTask(mySensor, myPump, T, MoistureDisplay);
            T.schedule(Task, 0, 1000);
        }

        HashMap<Integer, Long> myPairs = new HashMap<>();
        StdDraw.setXscale(-1, 60);
        StdDraw.setYscale(-0.18, 5);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(0, 0, 0, 5);
        StdDraw.line(0, 0, 60, 0);
        StdDraw.text(-2, 0, "0");
        StdDraw.text(-2, 5, "5");
        StdDraw.text(30, -0.2, "Time (Seconds)");
        StdDraw.text(60, -0.2, "60");
        StdDraw.text(-2, 2.5, "V");
        StdDraw.text(30, 5, "Voltage vs. Time Graph");
    }
}