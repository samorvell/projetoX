package com.samorvell.pontointeligente.api.utils;


import com.samorvell.pontointeligente.api.enums.TipoEnum;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PeriodUtil {

    private TipoEnum type;
    private LocalDateTime timeInitialLunch;
    private LocalDateTime timeFinalLunch;
    private LocalDateTime timeInitialWork;
    private LocalDateTime timeFinalWork;
    private LocalDateTime interval;
    private LocalTime dayWork;
    private LocalDateTime hours;



    public LocalTime getHourDayWork() {


        var testDuration = Duration.between(timeFinalLunch, timeInitialLunch);
        System.out.println("Duration: " + testDuration);
        interval = timeFinalLunch.minusHours(timeInitialLunch.getHour());
        hours = timeFinalWork.minusHours(timeInitialWork.getHour());
        dayWork = hours.minusHours(getInterval().getHour()).toLocalTime();

        return dayWork ;
    }

    //Retrieve lunch schedule
    public LocalDateTime getHourIntervalLunch() {

        interval = timeFinalLunch.minusHours(timeInitialLunch.getHour());

        return interval ;
    }

}