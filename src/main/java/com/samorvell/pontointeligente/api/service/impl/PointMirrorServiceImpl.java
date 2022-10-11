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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private PeriodUtil periodUtil;
    private LancamentoServiceImpl lancamentoService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    public Page<PointMirror> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Buscando lançamentos para o funcionário ID {}", funcionarioId);
        return this.pointMirrorRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Cacheable("pointMirrorById")//anotação para criação e configuração do cache, que esta no arquivo ehcach.xml
    public Optional<PointMirror> buscarPorId(Long id) {
        log.info("Buscando um pointMirror pelo ID {}", id);
        return Optional.ofNullable(this.pointMirrorRepository.getById(id));
    }

    public void remover(Long id) {
        log.info("Removendo o lançamento ID {}", id);
        this.pointMirrorRepository.deleteById(id);
    }

    @CachePut("pointMirrorById")//sempre que houver atualização no dado principal, é tbm autalizado no chache
    public PointMirror persistir(PointMirror pointMirror) {
        log.info("Persistindo o pointMirror: {}", pointMirror);
        return this.pointMirrorRepository.save(pointMirror);
    }


    @CachePut("mirrorPointPorId")//sempre que houver atualização no dado principal, é tbm autalizado no chache
    public List<Lancamento> genaraterPointMirror(List<Lancamento> lancamento, Long funcionarioId) {

        /*Map<String, PeriodUtil> periodMap = new HashMap<>();
        var periodo = new PeriodUtil();
        PointMirror pointMirror = new PointMirror();
        List<PointMirror> inBase = new ArrayList<>();

        for (Lancamento element : release) {

            pointMirror = new PointMirror();
            //inBase = this.pointMirrorRepository.findByFuncionarioId(funcionarioId);
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
                    pointMirror.setData(element.getData().toLocalDate());
                    pointMirror.setCreationDate(element.getDataCriacao());
                    pointMirror.setAccumulated(periodo.getDayWork());
                    pointMirror.setFuncionarioId(element.getFuncionario().getId());
                    var verPointMirror = pointMirror;
                    var dataMirror = verPointMirror.getData();
                    PointMirror finalPointMirror = pointMirror;


                    System.out.println("ver point mirror: " + verPointMirror);
                    System.out.println("data mirror: " + dataMirror);
                    this.pointMirrorRepository.save(pointMirror);
                    periodMap.clear();

                } else {
                    periodo.setTimeFinalLunch(element.getData());
                    var timeBreak = periodo.getHourIntervalLunch();
                    System.out.println("Intervalo almoço: " + timeBreak);
                }


            } else {
                periodo.setType(key);
                if (isWork) {
                    periodo.setTimeInitialWork(element.getData());
                } else {
                    periodo.setTimeInitialLunch(element.getData());
                }
                periodMap.put(key, periodo);

            }



        }
        log.info("Gravando espelho de ponto: {}", release);

        //return (release);
        log.info("Gravando espelho de ponto: {}", lancamento);
        */
        return (lancamento);
    }


    @CachePut("pointMirrorById")//sempre que houver atualização no dado principal, é tbm autalizado no chache
    public void genaratePointMirrorById(Page<Lancamento> release, Long funcionarioId) {



        Map<String, PeriodUtil> periodMap = new HashMap<>();
        PointMirror pointMirror = new PointMirror();
        var newRelease = release.stream().collect(Collectors.toList());

        newRelease.stream().forEach(element -> {
            var periodUtil = new PeriodUtil();
            var isWork = element.getTipo().toString().contains(TRABALHO);
            var isLunch = element.getTipo().toString().contains(ALMOCO);
            //var key = isLunch ? ALMOCO : isWork ? TRABALHO : " ";
            newRelease.stream().filter(e -> e.getTipo() == TipoEnum.INICIO_TRABALHO)
                    .map(e -> {
                        periodUtil.setTimeInitialWork(e.getData().toString());
                        var entra = e.getData();
                        System.out.println("Entrada trabalho: " + entra);
                        return e;
                    }).filter(e -> e.getTipo() == TipoEnum.INICIO_ALMOCO)
                    .map(e -> {
                        periodUtil.setTimeInitialLunch(e.getData().toString());
                        var teste = e.getData();
                        System.out.println("teste data inicio almoço: " + teste);
                        return e;
                    }).filter(e -> e.getTipo() == TipoEnum.TERMINO_ALMOCO)
                    .map(e -> {
                        periodUtil.setTimeFinalLunch(e.getData().toString());
                        var teste = e.getData();
                        System.out.println("teste data saida almoço: " + teste);
                        return e;
                    }).filter(e -> e.getTipo() == TipoEnum.TERMINO_TRABALHO)
                    .map(e -> {
                        periodUtil.setTimeFinalWork(e.getData().toString());
                        var teste = e.getData();
                        System.out.println("teste data sainda trabalho: " + teste);
                        return e;
                    });

        });


    }


    /**
     * Converte um LancamentoDto para uma entidade Lancamento.
     *
     * @param pointMirrorDto
     * @param result
     * @return Lancamento
     * @throws ParseException
     */
    /*private PointMirror converterDtoParaPointMirror(PointMirrorDto pointMirrorDto, BindingResult result)
            throws ParseException {
        Lancamento lancamento = new Lancamento();

        if (lancamento.getId().is) {
            Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDto.getId().get());
            if (lanc.isPresent()) {
                lancamento = lanc.get();
            } else {
                result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
            }
        } else {
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
        }

        lancamento.setDescricao(lancamentoDto.getDescricao());
        lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
        lancamento.setData(LocalDateTime.parse(lancamentoDto.getData(), dateFormat));

        if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
            lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
        } else {
            result.addError(new ObjectError("tipo", "Tipo inválido."));
        }

        return lancamento;
    }*/

}