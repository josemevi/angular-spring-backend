package com.jdmv.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jdmv.springboot.backend.apirest.models.entity.Cliente;
import com.jdmv.springboot.backend.apirest.models.entity.Region;

//CrudRepository is a super class for have the basic CRUD operations and more ready to go automatically
public interface IClienteDao extends JpaRepository<Cliente, Long> {
	
	@Query("from Region") // object name not table name
	public List <Region> Regions();

}
