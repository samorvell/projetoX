package com.samorvell.pontointeligente.api.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.samorvell.pontointeligente.api.security.JwtAuthenticationEntryPoint;
import com.samorvell.pontointeligente.api.security.filter.JwtAuthenticationTokenFilter;



//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	/** The unauthorized handler. */
//	@Autowired
//	private JwtAuthenticationEntryPoint unauthorizedHandler;
//
//	/** The user details service. */
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	/**
//	 * Configure authentication.
//	 *
//	 * @param authenticationManagerBuilder the authentication manager builder
//	 * @throws Exception the exception
//	 */
//	@Autowired 
//	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService);
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
//	 */
//	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	/**
//	 * Authentication token filter bean.
//	 *
//	 * @return the jwt authentication token filter
//	 * @throws Exception the exception
//	 */
//	@Bean
//	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//		return new JwtAuthenticationTokenFilter();
//	}
//
//	/* (non-Javadoc)
//	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
//	 */
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//        
//                // Disable CSRF for our Requests
//                .csrf()
//                .disable()
//                
//                // Validate URL accessed
//                .exceptionHandling()
//                .authenticationEntryPoint(unauthorizedHandler)
//                
//                // No session will be created by Spring Security
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                
//                // Authorized Request
//                .and()
//                .authorizeRequests()
//                .antMatchers("/auth/**",
//                        "/v2/api-docs", 
//                        "/swagger-resources/**", 
//                        "/swagger-ui.html", 
//                        "/webjars/**",
//                        "/configuration/security", 
//                        "/*/download/**",
//                        "/*/*/download/**",
//                        "/actuator/**",
//                        "/public/**",
//                        "/websocket/**",
//                        "/websocket",
//                        "/ws/**",
//                        "/template/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                
//                // Login Settings
//                .and()
//                .formLogin()
//                .loginPage("/api/auth")
//                
//                // Logout settings
//                .and()
//                .logout()    
//                .logoutUrl("/api/logout")
//                .logoutSuccessUrl("/api/auth")
//                .permitAll()
//                
//                // Filter to add CORS filter
//                .and()
//                .addFilterBefore(new CorsFilter(corsConfiguration()), LogoutFilter.class)
//                
//                // Filter to verify that the token is present in the Header
//                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//	}
//
//    /**
//     * Cors configuration.
//     *
//     * @return the url based cors configuration source
//     */
//    private UrlBasedCorsConfigurationSource corsConfiguration() {
//    	
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//		config.addAllowedOrigin("*");
//		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//    
//    /**
//     * Web mvc configurer.
//     *
//     * @return the web mvc configurer
//     */
//    @Bean
//    WebMvcConfigurer webMvcConfigurer() {
//    	return new WebMvcConfigurer() {
//    		@Override
//    		public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    			registry.addResourceHandler("/template/**").addResourceLocations("classpath:/template/");
//    			WebMvcConfigurer.super.addResourceHandlers(registry);
//    		}
//		};
//    }


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	   return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		 // Disable CSRF for our Requests
		httpSecurity.csrf().disable().
		
		
		exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/auth/**", "/api/cadastrar-pj", "/api/cadastrar-pf", "/v2/api-docs",
						"/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**")
				.permitAll().anyRequest().authenticated();
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		httpSecurity.headers().cacheControl();
	}
}
