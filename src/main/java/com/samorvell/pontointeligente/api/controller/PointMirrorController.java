package com.samorvell.pontointeligente.api.controller;

import com.samorvell.pontointeligente.api.dtos.LancamentoDto;
import com.samorvell.pontointeligente.api.model.Funcionario;
import com.samorvell.pontointeligente.api.model.Lancamento;
import com.samorvell.pontointeligente.api.model.PointMirror;
import com.samorvell.pontointeligente.api.response.Response;
import com.samorvell.pontointeligente.api.services.FuncionarioService;
import com.samorvell.pontointeligente.api.services.LancamentoService;
import com.samorvell.pontointeligente.api.services.PointMirrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pointMirror")
@CrossOrigin(origins = "*")
public class PointMirrorController {

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    private static final Logger log = LoggerFactory.getLogger(PointMirrorController.class);

    @Autowired
    private PointMirrorService pointMirrorService;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Bean
    public PointMirror pointMirror() throws Exception {
        return new PointMirror();
    }


    /**
            * Retorna soma dos lançamentos.
            *
            * @param
     * @return ResponseEntity<Response < LancamentoDto>>
     */
    @PostMapping(value = "/generatepointmirrors/{funcionarioId}")
    public ResponseEntity<Response<Page<LancamentoDto>>> genaratePointMirrorById(@PathVariable("funcionarioId") Long funcionarioId,
                                                                        @RequestParam(value = "data", required = false) LocalDate data,
                                                                        @RequestParam(value = "pag", defaultValue = "0") int pag,
                                                                        @RequestParam(value = "ord", defaultValue = "id") String ord,
                                                                        @RequestParam(value = "dir", defaultValue = "DESC") String dir,
                                                                        @RequestHeader(value = "companyId", required = true) Long companyId) {
        log.info("Buscando lançamentos para validação do espelho de ponto por ID do funcionário: {}, página: {}", funcionarioId, pag);
        Response<Page<LancamentoDto>> response = new Response<Page<LancamentoDto>>();
        PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Sort.Direction.valueOf(dir), ord);

        Page<Lancamento> release = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(funcionarioId);
        var compId = funcionario.get().getEmpresa().getId();

        if (release.isEmpty()) {
            log.info("Lançamento não encontrado para o ID: {}", funcionarioId);
            response.getErrors().add("Lançamento não encontrado para o id " + funcionarioId);
            return ResponseEntity.badRequest().body(response);
        }

        if (companyId != compId) {
            log.info("Company id do funcionario é diferente do empresa id pesquisado: {}", companyId);
            response.getErrors().add("Company id do funcionario é diferente do empresa id:"+compId+" pesquisado: " + companyId);
            return ResponseEntity.badRequest().body(response);
        }

        this.pointMirrorService.genaratePointMirrorById(release, funcionarioId);

        return ResponseEntity.ok(response);
    }

}
