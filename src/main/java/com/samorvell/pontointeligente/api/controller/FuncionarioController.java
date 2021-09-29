package com.samorvell.pontointeligente.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samorvell.pontointeligente.api.model.Funcionario;
import com.samorvell.pontointeligente.api.repository.FuncionarioRepository;
import com.samorvell.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	private FuncionarioService funcionarioService;
	
	private final FuncionarioRepository funcionarioRepository;

	
	public FuncionarioController(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
//	@Value("${paginacao.qtd_por_pagina}")
//	private int qtdPorPagina;

	@GetMapping("/listartodos")
	public ResponseEntity<List<Funcionario>> ListarTodos() {
		return ResponseEntity.ok(funcionarioRepository.findAll());
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Funcionario> salvar(@RequestBody Funcionario funcionario) {
		return ResponseEntity.ok(funcionarioRepository.save(funcionario));
	}
	
	

	/**
	 * Atualiza os dados de um funcionário.
	 * 
	 * @param id
	 * @param funcionarioDto
	 * @param result
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
//	@PutMapping(value = "/{id}")
//	public ResponseEntity <FuncionarioDto> atualizar(@PathVariable("id") Long id,
//			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
//		log.info("Atualizando funcionário: {}", funcionarioDto.toString());
//		FuncionarioDto response = new FuncionarioDto();
//
//		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
//		if (!funcionario.isPresent()) {
//			result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
//		}
//
//		this.atualizarDadosFuncionario(funcionario.get(), response, result);
//
//		if (result.hasErrors()) {
//			log.error("Erro validando funcionário: {}", result.getAllErrors());
//			
//			return ResponseEntity.badRequest().body(response);
//		}
//
//		this.funcionarioService.persistir(funcionario.get());
//		response.setData(this.converterFuncionarioDto(funcionario.get()));
//
//		return ResponseEntity.ok(response);
//	}

	/**
	 * Atualiza os dados do funcionário com base nos dados encontrados no DTO.
	 * 
	 * @param funcionario
	 * @param funcionarioDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
//	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result)
//			throws NoSuchAlgorithmException {
//		funcionario.setNome(funcionarioDto.getNome());
//
//		if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
//			this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
//					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
//			funcionario.setEmail(funcionarioDto.getEmail());
//		}
//
//		funcionario.setQtdHorasAlmoco(null);
//		funcionarioDto.getQtdHorasAlmoco()
//				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
//
//		funcionario.setQtdHorasTrabalhoDia(null);
//		funcionarioDto.getQtdHorasTrabalhoDia()
//				.ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
//
//		funcionario.setValorHora(null);
//		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
//
//		if (funcionarioDto.getSenha().isPresent()) {
//			funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
//		}
//	}

	/**
	 * Retorna um DTO com os dados de um funcionário.
	 * 
	 * @param funcionario
	 * @return FuncionarioDto
	 */
//	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
//		FuncionarioDto funcionarioDto = new FuncionarioDto();
//		funcionarioDto.setId(funcionario.getId());
//		funcionarioDto.setEmail(funcionario.getEmail());
//		funcionarioDto.setNome(funcionario.getNome());
//		funcionario.getQtdHorasAlmocoOpt().ifPresent(
//				qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
//		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
//				qtdHorasTrabDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
//		funcionario.getValorHoraOpt()
//				.ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));
//
//		return funcionarioDto;
//	}
}
