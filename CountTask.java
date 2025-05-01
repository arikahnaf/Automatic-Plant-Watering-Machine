package eecs1021;

import org.firmata4j.ssd1306.SSD1306;
import java.util.TimerTask;

public class CountTask extends TimerTask{
    private int countValue = 10;
    private final SSD1306 theOledObject;
    public CountTask(SSD1306 aDisplayObject){
        this.theOledObject = aDisplayObject;
    }

    @Override
    public void run() {
        if(countValue == -1){
            countValue = 10;
        }

        theOledObject.getCanvas().clear();
        theOledObject.getCanvas().drawString(0,0,String.valueOf(countValue));
        theOledObject.display();
        countValue--;
    }
}