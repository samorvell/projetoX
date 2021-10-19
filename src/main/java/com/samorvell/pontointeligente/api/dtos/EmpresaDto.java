package com.samorvell.pontointeligente.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpresaDto {

	private Long id;
	private String razaoSocial;
	private String cnpj;

	
}
