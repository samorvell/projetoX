package com.samorvell.pontointeligente.api.service.impl;


import com.samorvell.pontointeligente.api.abstratct.PointMirrorAbstract;
import com.samorvell.pontointeligente.api.enums.TipoEnum;
import com.samorvell.pontointeligente.api.model.Lancamento;
import com.samorvell.pontointeligente.api.model.PointMirror;
import com.samorvell.pontointeligente.api.repository.FuncionarioRepository;
import com.samorvell.pontointeligente.api.repository.PointMirrorRepository;
import com.samorvell.pontointeligente.api.services.PointMirrorService;
import com.samorvell.pontointeligente.api.utils.PeriodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.time.ZoneId.systemDefault;
import static java.time.ZoneOffset.UTC;

@Service
public class PointMirrorServiceImpl implements PointMirrorService {

    private static final Logger log = LoggerFactory.getLogger(PointMirrorServiceImpl.class);
    private static final String ALMOCO = "ALMOCO";
    private static final String INICIO = "INICIO";
    private static final String TRABALHO = "TRABALHO";

    @Autowired
    private PointMirrorRepository pointMirrorRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @CachePut("mirrorPointPorId")//sempre que houver atualização no dado principal, é tbm autalizado no chache
    public Page<Lancamento> saveMirrorPointById(Page<Lancamento> lancamentos, Long funcionarioId) {

       /* Map<String, PeriodUtil> periodMap = new HashMap<>();
        //var periodo = new PeriodUtil();
        PointMirror pointMirror = new PointMirror();

        *//*for (Lancamento element : lancamentos) {

            //pointMirror = new PointMirror();
            var type = element.getTipo();
            var isStart = type.toString().contains("INICIO");
            var isFinish = type.toString().contains("TERMINO");
            var isLunch = type.toString().contains("ALMOCO");
            var isWork = type.toString().contains("TRABALHO");
            var key = isLunch ? "ALMOCO" : isWork ? "TRABALHO" : " ";

            if (periodMap.containsKey(key)) {

                periodo = periodMap.get(key);
                if (isWork) {
                    periodo.setTimeFinalWork(element.getData());
                    periodo.getHourDayWork();
                    System.out.println("horas trabalhadas dia: " + periodo.getDayWork());
                    pointMirror.setNome(element.getFuncionario().getNome());
                    pointMirror.setData(LocalDateTime.from(element.getData().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDate()));
                    pointMirror.setData(LocalDateTime.from(element.getData().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDate()));
                    pointMirror.setDataHora(element.getData().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDateTime());
                    pointMirror.setCriationDate(element.getDataCriacao().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDateTime());
                    pointMirror.setAccumulated(periodo.getDayWork());
                    pointMirror.setFuncionarioId(funcionarioId);
                    var verPointMirror = pointMirror;
                    var dataMirror = verPointMirror.getDataHora();
                    PointMirror finalPointMirror = pointMirror;


                    System.out.println("ver point mirror: " + verPointMirror);
                    System.out.println("data mirror: " + dataMirror);
                    periodMap.clear();

                } else {
                    periodo.setTimeFinalLunch(element);
                    var timeBreak = periodo.getHourIntervalLunch();
                    System.out.println("Intervalo almoço: " + timeBreak);
                }


            } else {
                periodo.setType(key);
                if (isWork) {
                    periodo.setTimeInitialWork(element);
                } else {
                    periodo.setTimeInitialLunch(element);
                }
                periodMap.put(key, periodo);

            }



        }
        log.info("Gravando espelho de ponto: {}", lancamentos);*//*

         */return (lancamentos);
    }

    /*  @Override
      public Optional<Lancamento> saveBMirrorPointById(Optional<Lancamento> launch, Long funcionarioId) {
          return Optional.empty();
      }
  */

    public void saveBMirrorPointById(Page<Lancamento> lancamentos, Long funcionarioId) {


        Map<String, PeriodUtil> periodMap = new HashMap<>();
        PointMirror pointMirror = new PointMirror();

        for (Lancamento element : lancamentos) {
            var type = element.getTipo().toString();
            if (type.contains("INICIO") || type.contains("TERMINO")) {
                var key = type.contains("ALMOCO") ? "ALMOCO" : type.contains("TRABALHO") ? "TRABALHO" : "";
                var periodo = periodMap.getOrDefault(key, new PeriodUtil(key));
                if (key.equals("TRABALHO")) {
                    periodo.setTimeFinalWork(element.getData());
                    var accumulated = periodo.getHourDayWork();
                    pointMirror.setNome(element.getFuncionario().getNome());
                    pointMirror.setData(LocalDateTime.from(element.getData().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDate()));
                    //pointMirror.setDataHora(element.getData().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDateTime());
                    //pointMirror.setCriationDate(element.getDataCriacao().toInstant(ZoneOffset.UTC).atZone(ZoneId.systemDefault()).toLocalDateTime());
                    pointMirror.setAccumulated(accumulated);
                    pointMirror.setFuncionarioId(funcionarioId);
                    periodMap.clear();
                } else {
                    periodo.setTimeFinalLunch(element);
                    var timeBreak = periodo.getHourIntervalLunch();
                }
                periodMap.put(key, periodo);
            }
        }
        //return lancamentos;


    }


}