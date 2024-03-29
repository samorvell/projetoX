package com.samorvell.pontointeligente.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.samorvell.pontointeligente.api.model.Lancamento;

@Service
public interface LancamentoService {

	/**
	 * Retorna uma lista paginada de lançamentos de um determinado funcionário.
	 *
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 */
	Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);

	/**
	 * Retorna um lançamento por ID.
	 *
	 * @param id
	 * @return Optional<Lancamento>
	 */
	Optional<Lancamento> buscarPorId(Long id);

	/**
	 * Persiste um lançamento na base de dados.
	 *
	 * @param lancamento
	 * @return Lancamento
	 */
	Lancamento persistir(Lancamento lancamento);

	/**
	 * Remove um lançamento da base de dados.
	 *
	 * @param id
	 */
	void remover(Long id);

	Optional<Lancamento> findReleaseByFuncionarioId(Long funcionarioId);
}