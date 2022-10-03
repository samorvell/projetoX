package com.samorvell.pontointeligente.api.repository;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.el.stream.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.samorvell.pontointeligente.api.model.Funcionario;


@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "FuncionarioRepository.findByEmpresaId",
				query = "SELECT func FROM Funcionario func WHERE func.empresa.id = :companyId") })
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Funcionario findByCpf(String cpf);

	Funcionario findByEmail(String email);

	Funcionario findByCpfOrEmail(String cpf, String email);

	Page<Funcionario> findAllByEmpresaId (@Param("companyId")Long companyId, Pageable pageable);

	Funcionario findFuncionarioById(Long id);
}