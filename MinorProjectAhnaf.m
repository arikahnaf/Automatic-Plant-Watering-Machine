function state = MinorProjectAhnaf
a = arduino ('COM3', 'uno');

figure(1)
h = animatedline;
ax = gca;
ax.YGrid = 'on';
ax.YLim = [0 5];
title ('Voltage of Moisture Sensor vs Time Graph');
xlabel ('Time [HH:MM:SS]');
ylabel ('Voltage [volts]');
startTime = datetime ('now');

stop = 0;

while ~stop
    moist = readVoltage (a, 'A1');
    t = datetime ('now') - startTime;
    addpoints (h, datenum(t), moist);
    ax.XLim = datenum ([t-seconds(15) t]);
    datetick ('x', 'keeplimits');
    drawnow;

    if moist > 3.4
        state = "Soil is dry, water required.";
        disp (state);
        writeDigitalPin (a, 'D2', 1);

    elseif moist > 2.8
        state = "Water required";
        disp (state);
        writeDigitalPin (a, 'D2', 1);

    elseif moist <= 2.8
        state = "Soil is wet, water no longer required.";
        disp (state);
        writeDigitalPin (a, 'D2', 0);

    else
        state = "System Error";
        disp (state);
        
    end
    
    stop = readDigitalPin (a, 'D6');
 
end
end