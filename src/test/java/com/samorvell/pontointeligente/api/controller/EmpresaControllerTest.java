package com.samorvell.pontointeligente.api.controller;


import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

//	@Autowired
//	private MockMvc mvc;
//
//	@MockBean
//	private EmpresaService empresaService;
//
//	private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
//	private static final Long ID = Long.valueOf(1);
//	private static final String CNPJ = "51463645000100";
//	private static final String RAZAO_SOCIAL = "Empresa XYZ";
//
//	@Test
//	@WithMockUser
//	public void testBuscarEmpresaCnpjInvalido() throws Exception {
//		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
//
//		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isBadRequest())
//				.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " + CNPJ));
//	}
//
//	@Test
//	@WithMockUser
//	public void testBuscarEmpresaCnpjValido() throws Exception {
//		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString()))
//				.willReturn(Optional.of(this.obterDadosEmpresa()));
//
//		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.data.id").value(ID))
//				.andExpect(jsonPath("$.data.razaoSocial", equalTo(RAZAO_SOCIAL)))
//				.andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
//				.andExpect(jsonPath("$.errors").isEmpty());
//	}
//
//	private Empresa obterDadosEmpresa() {
//		Empresa empresa = new Empresa();
//		empresa.setId(ID);
//		empresa.setRazaoSocial(RAZAO_SOCIAL);
//		empresa.setCnpj(CNPJ);
//		return empresa;
//	}
}
