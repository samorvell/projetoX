package com.samorvell.pontointeligente.api.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samorvell.pontointeligente.api.model.Lancamento;
import com.samorvell.pontointeligente.api.repository.LancamentoRepository;



@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//	@Autowired
//	private LancamentoService lancamentoService;
//
//	@Autowired
//	private FuncionarioService funcionarioService;
	
	private final LancamentoRepository lancamentoRepository;
	
	public LancamentoController(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}
	
//	@Value("${paginacao.qtd_por_pagina}")
//	private int qtdPorPagina;

	@GetMapping("/listartodos")
	public ResponseEntity<List<Lancamento>> ListarTodos() {
		return ResponseEntity.ok(lancamentoRepository.findAll());
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Lancamento> salvar(@RequestBody Lancamento lancamento) {
		return ResponseEntity.ok(lancamentoRepository.save(lancamento));
	}
	
	

	
}
