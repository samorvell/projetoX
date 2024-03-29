package com.samorvell.pontointeligente.api.service.impl;

import java.util.List;
import java.util.Optional;

import com.samorvell.pontointeligente.api.model.PointMirror;
import com.samorvell.pontointeligente.api.services.PointMirrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.samorvell.pontointeligente.api.model.Lancamento;
import com.samorvell.pontointeligente.api.repository.LancamentoRepository;
import com.samorvell.pontointeligente.api.services.LancamentoService;


@Service
public class LancamentoServiceImpl implements LancamentoService {

	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lançamentos para o funcionário ID {}", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	public Optional<Lancamento> findReleaseByFuncionarioId(Long funcionarioId) {
		log.info("Buscando lançamentos para o funcionário ID {}", funcionarioId);
		return this.lancamentoRepository.findEntriesByFuncionarioId(funcionarioId);
	}

	@Cacheable("lancamentoPorId")//anotação para criação e configuração do cache, que esta no arquivo ehcach.xml
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando um lançamento pelo ID {}", id);
		return Optional.ofNullable(this.lancamentoRepository.getById(id));
	}
	
	@CachePut("lancamentoPorId")//sempre que houver atualização no dado principal, é tbm autalizado no chache
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo o lançamento: {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}
	
	public void remover(Long id) {
		log.info("Removendo o lançamento ID {}", id);
		this.lancamentoRepository.deleteById(id);
	}

}
