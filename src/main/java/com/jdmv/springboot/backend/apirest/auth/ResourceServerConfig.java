package com.jdmv.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//This class will manage the configuration of the clients accessing to the resource server
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	//only implement this method to allow the control of all security rules of the end points
	//this's the Oauth2 side of validation and managing resources 
	//we also have to put this method in spring security configuration
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//add the scope of each end point
		// .anyRequest().authenticated() is always put at the end of the rules declared here for any route
		//that we don't have permissions assigned
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**", "/images/**").permitAll()
		.antMatchers("/api/clientes/{id}").permitAll()
		.antMatchers("/api/facturas/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
//		.antMatchers("/api/clientes/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
		
		
		//to add custom security access we use antMatchers and .hasrole, hasAnyRole or permitall to specify 
		//a access limitation
		//When we put the role name isn't necessary to let the ROLE_ prefix
		//When assigning the permission always starts with the specific one to the most general.
		//In antMatchers the ** means any route this's a regexp
		//.antMatchers("/api/clientes/**").hasRole("ADMIN") specifies that
		//all that subsequent routes and methods will require admin role.
		
		//WE ACTUALLY going to move all the permission to controllers annotations so we comment all the routes except the first one
		//since we're specifying .anyRequest().authenticated();
		//to activate the annotation we need to do it in the spring sec config
		
		// /images/** is the resource/static folder for images, we giving all the images public access
		
		
		//.and() to return to the principal object 
		//cors().configurationSource(corsConfigurationSource()) to load the cors config
	}
	
	//Adding CORS rules to the resource server since we're using oauth2 and jwt
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		//CORS configuration
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
		
		//Adding the config to the routes
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
		
		//We add the cors config to the ep configuration access
		
		//The config.setAllowedOrigins(Arrays.asList("*")); means to all the domains
		//The config.setAllowedMethods(Arrays.asList("*")); means all the methods
		
	}
	
	//Adding a spring filter in the top priority of the context to register the cors config
	//In this way the config is added to the auth server when accessing and validation the token
	//With this we config the cors to spring security and Oauth2
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		// creating the cors filter with the existing config
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		//Registering the bean to the spring bean collection
		//Giving a low order, when the order is low the precedence or priority is high
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	
	

}
