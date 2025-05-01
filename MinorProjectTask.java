package eecs1021;

import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MinorProjectTask extends TimerTask
{
    private static Integer clock=0;
    private final Pin mySensor;
    private final Pin myPump;
    private final SSD1306 MoistureDisplay;
    private final Timer time;
    private HashMap<Integer, Double> ANALog;

    MinorProjectTask(Pin pin, Pin myPump, Timer time, SSD1306 display)
    {
        this.mySensor = pin;
        this.myPump = myPump;
        this.time = time;
        this.MoistureDisplay = display;
    }

    @Override
    public void run()
    {
        clock++;

        this.MoistureDisplay.getCanvas().clear();

        long Value = mySensor.getValue();

        ANALog = new HashMap<>();

        double voltage = (5.0 / 1023.0) * Value;
        ANALog.put(clock, voltage);

        ANALog.forEach((xValue,yValue) -> StdDraw.textRight(xValue,yValue,"*"));

        String L = String.valueOf(mySensor.getValue());
        System.out.println("Moisture level: " + L);

        if(this.mySensor.getValue()>= 700)
        {
            try
            {
                System.out.println("Dry soil. Moisture level: " + L);
                MoistureDisplay.getCanvas().clear();
                MoistureDisplay.getCanvas().drawString(0,0, L);
                MoistureDisplay.display();
                myPump.setValue(1);
            }
            catch(Exception ex) {}
        }
        else if (this.mySensor.getValue() <= 550)
        {
            try
            {
                System.out.println("Soil is sufficiently wet. Moisture level: " + L);
                MoistureDisplay.getCanvas().clear();
                MoistureDisplay.getCanvas().drawString(0, 0, L);
                MoistureDisplay.display();
                myPump.setValue(0);
                MoistureDisplay.getCanvas().clear();
            }
            catch (Exception ex) {}
        }
    }
}