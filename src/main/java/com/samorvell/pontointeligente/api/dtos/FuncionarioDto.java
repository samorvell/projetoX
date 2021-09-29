package com.samorvell.pontointeligente.api.dtos;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.validation.ObjectError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FuncionarioDto {
	
	private Long id;
	private String nome;
	private String email;
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Size(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	@NotEmpty(message = "Nome não pode ser vazio.")
	public String getNome() {
		return nome;
	}

	public List<ObjectError> getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setData(FuncionarioDto converterFuncionarioDto) {
		// TODO Auto-generated method stub
		
	}

	

}
