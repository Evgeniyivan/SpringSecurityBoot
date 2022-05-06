package users.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage("/login")

				.successHandler(loginSuccessHandler)
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll();

		http.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.and().csrf().disable();

		http
				.authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/user").hasAuthority("USER")
				.antMatchers("/**").access("hasAuthority('ADMIN')").anyRequest().authenticated();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}