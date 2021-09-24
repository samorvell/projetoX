package com.samorvell.pontointeligente.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samorvell.pontointeligente.api.model.Empresa;
import com.samorvell.pontointeligente.api.repository.EmpresaRepository;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*")
public class EmpresaController {

	private final EmpresaRepository empresaRepository;

	public EmpresaController(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	@GetMapping("/listartodos")
	public ResponseEntity<List<Empresa>> ListarTodos() {
		return ResponseEntity.ok(empresaRepository.findAll());
	}

	@PostMapping("/salvar")
	public ResponseEntity<Empresa> salvar(@RequestBody Empresa empresa) {
		return ResponseEntity.ok(empresaRepository.save(empresa));
	}

}
