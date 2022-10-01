package com.samorvell.pontointeligente.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.ToString;

@ToString
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

		private static final long serialVersionUID = 3960436649365666213L;

		private Long id;
		private String razaoSocial;
		private String cnpj;
		private LocalDateTime dataCriacao;
		private LocalDateTime dataAtualizacao;
		private List<Funcionario> funcionarios;

		public Empresa() {
		}

		@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Column(name = "razao_social", nullable = false)
		public String getRazaoSocial() {
			return razaoSocial;
		}

		public void setRazaoSocial(String razaoSocial) {
			this.razaoSocial = razaoSocial;
		}

		@Column(name = "cnpj", nullable = false)
		public String getCnpj() {
			return cnpj;
		}

		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
		}

		@Column(name = "data_criacao", nullable = false)
		public LocalDateTime getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(LocalDateTime dataCriacao) {
			this.dataCriacao = dataCriacao;
		}

		@Column(name = "data_atualizacao", nullable = false)
		public LocalDateTime getDataAtualizacao() {
			return dataAtualizacao;
		}

		public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
			this.dataAtualizacao = dataAtualizacao;
		}

		@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		public List<Funcionario> getFuncionarios() {
			return funcionarios;
		}

		public void setFuncionarios(List<Funcionario> funcionarios) {
			this.funcionarios = funcionarios;
		}

		@PreUpdate
	    public void preUpdate() {
	        dataAtualizacao = LocalDateTime.now();
	    }

	    @PrePersist
	    public void prePersist() {
	        final LocalDateTime atual = LocalDateTime.now();
	        dataCriacao = atual;
	        dataAtualizacao = atual;
	    }



}
