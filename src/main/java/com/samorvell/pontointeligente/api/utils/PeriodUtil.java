package com.samorvell.pontointeligente.api.utils;


import com.samorvell.pontointeligente.api.enums.TipoEnum;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PeriodUtil {

    private String turn;
    private String timeInitialLunch;
    private String timeFinalLunch;
    private String timeInitialWork;
    private String timeFinalWork;
    private LocalDateTime interval;
    private LocalTime dayWork;
    private LocalTime hours;

    public LocalTime getHourDayWork() {


        var testDuration = Duration.between(LocalDateTime.parse(timeFinalLunch) ,LocalDateTime.parse(timeInitialLunch));
        System.out.println("Duration: " + testDuration);

        interval = LocalDateTime.parse(timeFinalLunch).minusHours(LocalDateTime.parse(timeInitialLunch).getHour());

        hours = LocalTime.parse(timeFinalWork).minusHours(LocalTime.parse(timeInitialWork).getHour());
        dayWork = hours.minusHours(getInterval().getHour());

        return dayWork ;
    }

    //Retrieve lunch schedule
    public LocalDateTime getHourIntervalLunch() {

        interval = LocalDateTime.parse(timeFinalLunch).minusHours(LocalDateTime.parse(timeInitialLunch).getHour());

        return interval ;
    }

}