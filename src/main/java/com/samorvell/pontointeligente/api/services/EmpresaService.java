package com.samorvell.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.samorvell.pontointeligente.api.model.Empresa;

@Service
public interface EmpresaService {
	
	/**
	 * Retorna uma empresa dado um CNPJ.
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Cadastra uma nova empresa na base de dados.
	 * 
	 * @param empresa
	 * @return Empresa
	 */
	Empresa persistir(Empresa empresa);

}
