package com.samorvell.pontointeligente.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samorvell.pontointeligente.api.response.Response;
import com.samorvell.pontointeligente.api.security.dto.JwtAuthenticationDto;
import com.samorvell.pontointeligente.api.security.dto.TokenDto;
import com.samorvell.pontointeligente.api.security.utils.JwtTokenUtil;


@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Gera e retorna um novo token JWT.
	 * 
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<TokenDto>>
	 * @throws AuthenticationException
	 */
	@PostMapping
	public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto, 
															//@RequestHeader("companyId") Integer companyId,
															BindingResult result) throws AuthenticationException {
		Response<TokenDto> response = new Response<TokenDto>();

		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		log.info("Gerando token para o email {}.", authenticationDto.getEmail());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Funcionario model = ;

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		String token = jwtTokenUtil.obterToken(userDetails);
		response.setData(new TokenDto(token));

		return ResponseEntity.ok(response);
	}

	/**
	 * Gera um novo token com uma nova data de expiração.
	 * 
	 * @param request
	 * @return ResponseEntity<Response<TokenDto>>
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {
		log.info("Gerando refresh token JWT.");
		Response<TokenDto> response = new Response<TokenDto>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}

		if (!token.isPresent()) {
			response.getErrors().add("Token não informado.");
		} else if (!jwtTokenUtil.tokenValido(token.get())) {
			response.getErrors().add("Token inválido ou expirado.");
		}

		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response);
		}

		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		response.setData(new TokenDto(refreshedToken));
		return ResponseEntity.ok(response);
	}

	/**
	 * Convert the model to presenter
	 *
	 * @param model registered user
	 * @return presenter
	 */
//	private AutenticationPresenter convertModelToPresenter(Funcionario model) {
//
//		AutenticationPresenter presenter = new AutenticationPresenter();
//		presenter.setToken(token);
//		presenter.setToken(jwtTokenUtil.getToken(model));
//		presenter.setExpirationDatePassword(model.getTsPasswordExpiration());
//		presenter.setUserId(model.getCgUser());
//		presenter.setLogin(model.getDsLogin());
//		presenter.setStartDateAccess(model.getDhEnd());
//		presenter.setEndDateAccess(model.getDhStart());
//		presenter.setUserName(model.getNaUser());
//		presenter.setEmail(model.getDsEmail());
//		Optional.ofNullable(termsLocalUserService.findById(model.getCgUser())).ifPresent(terms -> {
//			presenter.setTerms(TermsLocalUserPresenter.builder()
//					.accepted(LmApplicationUtils.integerToBooleanDefaultFalse(terms.getInAccepted()))
//					.ethicalAccepted(LmApplicationUtils.integerToBooleanDefaultFalse(terms.getInEthicalAccepted()))
//					.hspAccepted(LmApplicationUtils.integerToBooleanDefaultFalse(terms.getInHspAccepted())).build());
//		});
//		presenter.setType(new TypePresenter(model.getCdTypeAccess(), model.getTypeAccessModel().getNaTypeAccess()));
//		presenter.setQmsEnabled(Optional.ofNullable(model.getDhQms()).map(qms -> true).orElse(false));
//		if (model.getTsPasswordExpiration() != null && model.getTsPasswordExpiration().after(new Date())
//				&& model.getDfPasswordSituation().equals(PasswordSituationType.CHANGE_REQUIRED.getId())) {
//			presenter.setChangePassword(true);
//		}
//		presenter.setPremiumEnabled(false);
//		if (BooleanType.YES.getId().equals(model.getIsPremium())) {
//
//			Date currentPremiumDate = model.getLsLocalUserPremium().stream().map(LocalUserPremiumModel::getDhPremium)
//					.sorted(Comparator.reverseOrder()).findFirst().orElseThrow();
//			presenter.setPremiumEnabled(
//					currentPremiumDate.compareTo(DateUtil.toDate(LocalDateTime.now().minusHours(INTERVAL_24H))) < 0);
//		}
//
//		return presenter;
//	}

}
