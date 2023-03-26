package com.samorvell.pontointeligente.api.abstratct;

import com.samorvell.pontointeligente.api.model.Lancamento;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public abstract class PointMirrorAbstract {

    public LocalDateTime getHourIntervalLunch(List<LocalDateTime> timeInitialLunch, List<LocalDateTime> timeFinalLunch) {

        //timeInitialLunch.stream().collect();

        return  null ;//timeFinalLunch.minusHours(timeInitialLunch.getHour());
    }

}
