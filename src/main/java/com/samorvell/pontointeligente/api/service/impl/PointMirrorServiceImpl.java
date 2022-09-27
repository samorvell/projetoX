package com.samorvell.pontointeligente.api.service.impl;


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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.ZoneId;
import java.util.*;

@Service
public class PointMirrorServiceImpl implements PointMirrorService {


    private static final Logger log = LoggerFactory.getLogger(PointMirrorServiceImpl.class);
    private static final String ALMOCO = "ALMOCO";
    private static final String INICIO = "INICIO";
    private static final String TRABALHO = "TRABALHO";

    @Autowired
    private PointMirrorRepository pointMirrorRepository;

    @Override
    public Page<PointMirror> findPointMirrorByFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        return null;
    }

    @Autowired
    private Optional<FuncionarioRepository> funcionarioRepository;

    @Override
    public Optional<PointMirror> buscarPorMirrorId(Long mirrorId) {
        return Optional.empty();
    }

    @Override
    public PointMirror persistir(PointMirror pointMirror) {
        return null;
    }

    /*@Override
    public void remover(Long mirrorId) {

    }*/

   /* @Override
    public List<PointMirror> findPointMirrorByFuncionarioId(Long funcionarioId) {
        return null;
    }*/


    @CachePut("mirrorPointPorId")//sempre que houver atualização no dado principal, é tbm autalizado no chache
    public List<Lancamento> saveMirrorPointById(List<Lancamento> lancamento, Long funcionarioId) {

        /*Map<String, PeriodUtil> periodMap = new HashMap<>();
        var periodo = new PeriodUtil();
        PointMirror pointMirror = new PointMirror();
        List<PointMirror> inBase = new ArrayList<>();

        for (Lancamento element : lancamento) {

            pointMirror = new PointMirror();
            inBase = this.pointMirrorRepository.findByFuncionarioId(funcionarioId);
            var type = element.getType();
            var isStart = type.toString().contains("INICIO");//true
            var isFinish = type.toString().contains("TERMINO");//false
            var isLunch = type.toString().contains("ALMOCO");//false
            var isWork = type.toString().contains("TRABALHO");//true
            var key = isLunch ? "ALMOCO" : isWork ? "TRABALHO" : " ";



            if (periodMap.containsKey(key)) {

                periodo = periodMap.get(key);
                if (isWork) {
                    periodo.setTimeFinalWork(element);
                    periodo.getHourDayWork();
                    System.out.println("horas trabalhadas dia: " + periodo.getDayWork());
                    pointMirror.setNome(element.getEmployee().getNome());
                    pointMirror.setHora(element.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
                    pointMirror.setData(element.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    pointMirror.setDataHora(element.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    pointMirror.setCriationDate(element.getDataCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
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



        log.info("Gravando espelho de ponto: {}", lancamento);
        */
        return (lancamento);
    }

    @Override
    public Optional<Lancamento> saveBMirrorPointById(Optional<Lancamento> launch, Long funcionarioId) {
        return Optional.empty();
    }

    /*@CachePut("lancamentoPorId")//sempre que houver atualização no dado principal, é tbm autalizado no chache
    public Optional<Lancamento> saveBMirrorPointById(Optional<Lancamento> lancamento, Long funcionarioId) {

        PointMirror pointMirror = new PointMirror();
        PeriodUtil periodUtil = new PeriodUtil();
        Map<String, PeriodUtil> periodMap = new HashMap<>();
        var period = new PeriodUtil();
        var inBase = this.pointMirrorRepository.findByFuncionarioId(funcionarioId);

        lancamento.stream().forEach(element -> {
            var type = element.getTipo();
            var isStart = type.toString().contains("INICIO");
            var isFinish = type.toString().contains("TERMINO");
            var isLunch = type.toString().contains("ALMOCO");
            var isWork = type.toString().contains("TRABALHO");
            var key = isLunch ? "ALMOCO" : isWork ? "TRABALHO" : " ";

        });

        ;


            *//*var periodUtil = new PeriodUtil();
            var isStart = lancamento.//getTipo().toString().contains(TRABALHO);//true,FALSE,FALSE,TRUE
            var isWork = element.getTipo().toString().contains(INICIO);//true,TRUE,FALSE,FALSE
            var isLunch = element.getTipo().toString().contains(ALMOCO);//FALSE,TRUE,TRUE,FALSE
            var keyPeriod = isLunch ? "ALMOCO" : isWork ? "TRABALHO" : " ";//TRABALHO,ALMOÇO,ALMOÇO,TRABALHO
            var nDate = element.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            var nHour = element.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();*//*


        return lancamento;

    }*/
}