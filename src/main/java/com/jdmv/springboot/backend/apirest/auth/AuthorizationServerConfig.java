package com.jdmv.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


//NOTE implementing the AuthorizationServerConfigurerAdapter is not deprecated is newer releases.
//We are using a old version of org.springframework.security.oauth and org.springframework.security
// in order to work in this example 
// only oauth2 client is supported in the lasted releases and the server has to be configured manually
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//Qualifier to specify the bean has to be taken from that method that returns the object
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	//Injecting the additional info componet for the token extra info
	@Autowired
	private TokenAdditionalInfo infoAdditionalToken;
	
	//This's the configuration of the permission of the end points (Ep of the Oauth2)
	//This ep are protected via Header Authorization Basic: Client id + Client secret
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//permit all grants permission to every user to request a token 
		//tokenKeyAccess specify the availability of the ep /oauth/token to request tokens 
		//checkTokenAccess ep that validates the token and his sign
		//isAuthenticated() only access to the users logged in 
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	
	//Oauth2
	//This is the configuration for each app that's going to grant access to the API
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		//in memory is the storage type of the clients object
		//secrets refers to the secret code that's going to be used to generate the tokens must be encode
		//scopes refers to the reach of the client (read only, write only, etc)
		//authorizedGrantTypes refers to the concession of the token, which method is going to be used to generated the token.
		//There're multiple ways of generating the token like "auth code" and "implicit"
		//refresh token renews the token every time we grant authorization
		//accessTokenValiditySeconds and refreshTokenValiditySeconds sets the validity in seconds of the token
		clients.inMemory()
		.withClient("angularapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read","write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
	}
	
	//This is the configuration of the authorization server
	//this EP is the responsible to all the process of authentication and token validation
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//We add both default and custom tokens together
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); 
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdditionalToken, accessTokenConverter()));
		
		//first register the auth manager
		//second register accesstokenconverter is a component that had to be implemented.
		//is in charge to store and manage data of the token (claims) e.g (user name, roles) 
		//Decode all this claims for authentication manager oauth2 can make the validation.
		//translates and validates the token data
		//under the hood this component is used by JWTTokenStorage (token persistence component)
		//JwtStorage requires JwtAccessTokenConverter and isn't required to implement JwtStorage
		//This will be auto if we don't implement it in a implicit form.
		//endpoints.authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter());
		
		//example doing the implemetation of token storage
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStorage())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
		//we add the token enhancer at the end with the token chain as param
	}
	
	@Bean
	public JwtTokenStore tokenStorage() {
	
		return new JwtTokenStore(accessTokenConverter());
	}
	
	//This is the secret key for generate the token
	//Here we can specify a secret code for the token, can be in multiple ways
	//this two methods below uses a simple MAC (authentication code) to generate the secrets
	//the first one is just using HS256 algorithm with the MAC (this's the default)
	//the other is use a RSA code, this will require a public and a private certificate  
	//the token is generated with the private key and is validated using the public key
	//the RSA methods should be in OpenSSH format
	//the secret key is always store in the server and can save in multiple ways.
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC);
		return jwtAccessTokenConverter;
	}
	
	
}
