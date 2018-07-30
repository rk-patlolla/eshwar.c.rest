/*package com.tejyasols.surveyAppRest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tejyasols.surveyAppRest.filters.RequestResponseFilter;
import com.tejyasols.surveyAppRest.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SurveyAppRestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	RequestResponseFilter requestResponseFilter;
	
	@Autowired
	SurveyAppRestAuthenticationEntryPoint surveyAppRestAuthenticationEntryPoint;
	
	@Autowired
	LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Value("${signup.url}")
	private String SIGN_UP_URL;
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, SIGN_UP_URL).authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()));

		// The pages does not require login
		http.csrf().disable().authorizeRequests()
				.antMatchers("loginController/home").hasAnyRole("ADMIN", "USER").anyRequest().authenticated()
				.antMatchers("admin/**").hasRole("ADMIN").anyRequest().authenticated()
				.antMatchers("user/**").hasRole("USER").anyRequest().authenticated()
				.antMatchers("/", "/js/**", "/css/**", "/image/**", "/webjars/**").permitAll()
				.and().httpBasic().authenticationEntryPoint(surveyAppRestAuthenticationEntryPoint)
				;//.and()
				
		
		//adding jwtfilters tokens to login
//		http.authorizeRequests().antMatchers("/loginController/login")

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Config for Login Form
		http.authorizeRequests().and().formLogin()//.loginPage("/login").permitAll()//
				// Submit URL of login page.
				.defaultSuccessUrl("/default")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password")
				// Config for Logout Page
				.and().logout().invalidateHttpSession(true)
				.clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
            .exceptionHandling();
                //.accessDeniedHandler(accessDeniedHandler);;

		// Config Remember Me.
		http.authorizeRequests().and() //
				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
				.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

	}

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
	
	
	
}
*/