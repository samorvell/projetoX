package com.samorvell.pontointeligente.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samorvell.pontointeligente.api.model.ClienteModel;

public interface ClienteRepository extends JpaRepository<ClienteModel, Integer>{

}
