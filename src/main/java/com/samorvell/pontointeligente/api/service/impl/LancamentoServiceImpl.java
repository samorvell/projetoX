package com.samorvell.pontointeligente.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.samorvell.pontointeligente.api.model.Periodo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	
	public List<Lancamento> buscarLancamentosPorFuncionarioId(Long funcionarioId) {
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



	@CachePut("lancamentoPorId")//sempre que houver atualização no dado principal, é tbm autalizado no chache
	public List<Lancamento> saveMirroPointById( List<Lancamento> lancamentos) {

		Map<String, Periodo> periodMap = new HashMap<>();

		for (Lancamento element : lancamentos) {

			var tipo = element.getTipo();
			var isAlmoco = tipo.toString().contains("ALMOCO");
			var isTrabalho = tipo.toString().contains("TRABALHO");
			var isTermino = tipo.toString().contains("TERMINO");
			var isInicio = tipo.toString().contains("INICIO");
			var key = isAlmoco ? "ALMOCO" : isTrabalho ? "TRABALHO" : " ";

			if (periodMap.containsKey(key)) {

				var periodo = periodMap.get(key);
				if (isInicio) {
					periodo.setRelaeseInitial(element);
					System.out.println("entrada chave trabalho: " + periodo.getRelaeseInitial());
				} else {
					periodo.setReleaseFinal(element);
					var teste = periodo.gethourInterval();
					System.out.println("intervalo? " + teste);
				}
			} else {
				var periodo = new Periodo();
				periodo.setType(key);
				if (isInicio) {
					periodo.setRelaeseInitial(element);

				} else {
					periodo.setReleaseFinal(element);
					var enterHour = periodo.getRelaeseInitial();
					var enterSend = periodo.gethourInterval();
					System.out.println("Hora de entrada trabalho: "+ enterHour);
					System.out.println("segunda entrada pq a primeira estava vazia: " + enterSend);
				}
				periodMap.put(key, periodo);
			}
		}


		//log.info("Persistindo o lançamento: {}", lancamentos);
		//this.lancamentoRepository.saveMirroPoint((Lancamento) lancamentos);
		return (lancamentos); //
	}


}
