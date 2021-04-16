package com.jdmv.springboot.backend.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//By default this class uses the implementation UserService
//We use configuration and WebSecurityConfigurerAdapter to specify the class as a cfg class

//this enables the @secure annotation to manage access permission in the controllers.
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//To inject the instance of implementing that interface, we only had one (UserService)
	@Autowired
	private UserDetailsService usuarioService;
	
	//Bean annotation to add this object to the context and make it available for accessing later
	//Bean annotations registers object that're returning from a method
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Autowired here to inject AuthenticationManagerBuilder
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	}
	
	@Bean("authenticationManager") //specifies the Bean name 
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//The spring side of adding security rules to the access of the resource server.
	//Here we need to deactivate the CSRF (Cross site request forgery)
	//This's to protect the forms with a token
	//We going to deactivate this since we're using angular to control the forms
	//.and method adds more rules to the http.authorizaRequest() 
	//We going to deactivate the session of spring since our api is stateless and we're using tokens
	//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//add the scope of each end point
		// .anyRequest().authenticated() is always put at the end of the rules declared here for any route
		//that we don't have permissions assigned
		//.antMatchers(HttpMethod.GET,"/api/clientes").permitAll() is removed here since is manage by Oauth2 
		//and not by spring security
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	

}
