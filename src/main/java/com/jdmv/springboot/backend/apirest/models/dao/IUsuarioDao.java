package com.jdmv.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jdmv.springboot.backend.apirest.models.entity.Usuario;


//in the generics put the entity class with the type of the Id
public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	//to implement custom queries we can used the annotation @query (using JSQL) or using query method name
	//by using a naming convention (findBy) JSQL will automatically do this: select u from Usuario where u.username=?1
	//And or Or specify others parameters and the inclusion type
	//public Usuario findByUsernameAndEmail(String username, String email);
	public Usuario findByUsername(String username);
	
	//using the query annotation
	//@Query("select u from Usuario u where u.username=?1 and u.otro=2?")
	//public Usuario findByUsername2(String username, String otro);
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String username);

}
