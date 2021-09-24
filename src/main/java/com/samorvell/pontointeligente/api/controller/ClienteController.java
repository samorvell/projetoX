package com.samorvell.pontointeligente.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samorvell.pontointeligente.api.model.ClienteModel;
import com.samorvell.pontointeligente.api.repository.ClienteRepository;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/listartodos")
	public ResponseEntity<List<ClienteModel>> ListarTodos() {
		return ResponseEntity.ok(clienteRepository.findAll());
	}

	@PostMapping("/salvar")
	public ResponseEntity<ClienteModel> salvar(@RequestBody ClienteModel clienteModel) {
		return ResponseEntity.ok(clienteRepository.save(clienteModel));
	}

	

}
