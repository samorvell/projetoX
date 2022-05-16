package com.samorvell.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.samorvell.pontointeligente.api.model.Funcionario;

@Service
public interface FuncionarioService {
	
	/**
	 * Persiste um funcionário na base de dados.
	 * 
	 * @param funcionario
	 * @return Funcionario
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Busca e retorna um funcionário dado um CPF.
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * Busca e retorna um funcionário dado um email.
	 * 
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Busca e retorna um funcionário por ID.
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorId(Long id);

	/**
	 * Busca todos os funcionarios baseado no company id.
	 * 
	 * @param companyId
	 * @return Optional<Funcionario>
	 *  */		
	Page<Funcionario> buscarFuncionarioPorEmpresaId(Long empresaId, PageRequest pageRequest);

	
}
