package com.samorvell.pontointeligente.api.utils;


import com.samorvell.pontointeligente.api.enums.TipoEnum;
import com.samorvell.pontointeligente.api.model.Lancamento;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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
    private LocalDateTime dayWork;
    private LocalDateTime hours;

    public PeriodUtil(String key) {
    }


    public List<LocalDateTime> getHourDayWork() {

        /*timeInitialWork = lancamentos.stream().filter(initWork -> initWork.getTipo().equals(TipoEnum.INICIO_TRABALHO))
                .map(Lancamento::getData)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado um lançamento de início de trabalho"));

        timeFinalWork = lancamentos.stream().filter(initWork -> initWork.getTipo().equals(TipoEnum.TERMINO_TRABALHO))
                .map(Lancamento::getData)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado um lançamento de término de trabalho"));

        var duration = Duration.between( timeFinalLunch,  timeInitialLunch);
        interval = timeFinalLunch.minusHours(timeInitialLunch.getHour());
        hours = timeFinalWork.minusHours(timeInitialWork.getHour());
        dayWork = hours.minusHours(getInterval().getHour());*/

        return Collections.singletonList(hours.minusHours(getInterval().getHour()));
    }

    //Retrieve lunch schedule
    public LocalDateTime getHourIntervalLunch() {

        interval = timeFinalLunch.minusHours(timeInitialLunch.getHour());

        return interval ;
    }


}