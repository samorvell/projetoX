package com.samorvell.pontointeligente.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

@Getter
@Setter
public class Periodo {

    private String type;
    private Lancamento relaeseInitial;
    private Lancamento releaseFinal;
    private Period interval;

    public Periodo(LocalDateTime toLocalDateTime, LocalDateTime toLocalDateTime1) {
    }

    public Periodo() {

    }


    public int gethourInterval() {

        Periodo periodo = new Periodo(
                relaeseInitial.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                releaseFinal.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        var interval = periodo.gethourInterval();

        System.out.println("inteiro sobre o intervalo: " + interval);
        return interval;
    }

}
