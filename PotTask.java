package eecs1021;

import org.firmata4j.Pin;
import org.firmata4j.ssd1306.MonochromeCanvas;
import org.firmata4j.ssd1306.SSD1306;
import java.util.Timer;
import java.util.TimerTask;

public class PotTask extends TimerTask {
    private final SSD1306 display;
    private final Pin myPin;
    private final Timer timer;
    private final int duration;

    public PotTask(SSD1306 display, Pin pin, Timer timer, int duration) {
        this.display = display;
        this.myPin = pin;
        this.timer = timer;
        this.duration = duration;
    }

    @Override
    public void run() {
        String PotValue = String.valueOf(myPin.getValue());
        System.out.println("Value: " + PotValue);
        display.getCanvas().fillRect(0,0,128,9,MonochromeCanvas.Color.DARK);
        display.getCanvas().drawString(0,0,"Value: " + PotValue);
        display.getCanvas().drawHorizontalLine(0,10,1023,MonochromeCanvas.Color.DARK);
        int lineLength = (int)myPin.getValue() * (128 - 1) / 1023;
        display.getCanvas().drawHorizontalLine(0,10,lineLength,MonochromeCanvas.Color.BRIGHT);
        display.display();
    }
}
