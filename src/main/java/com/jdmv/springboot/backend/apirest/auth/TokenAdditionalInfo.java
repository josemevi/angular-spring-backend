package com.jdmv.springboot.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.jdmv.springboot.backend.apirest.models.entity.Usuario;
import com.jdmv.springboot.backend.apirest.models.services.IUsuarioService;

//if we want to add additional data to the token we need to implement the token enhance interface
//this class must be a component of the spring context

//To actually add the extra info of the token we need to implement a instance of this class inside the AuthorizationServerEndpointsConfigurer method
//This's a extra to the AuthorizationServerConfig

@Component
public class TokenAdditionalInfo implements TokenEnhancer{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		//we can put any data we want into the token, trying not to put so many.
		Map<String, Object> info = new HashMap<>();
		info.put("additional_info", "hello ".concat(authentication.getName()));
		
		info.put("nombre", usuario.getNombre());
		info.put("apellido", usuario.getApellido());
		info.put("email", usuario.getEmail());
		
		//to add the args to the token we do this
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
