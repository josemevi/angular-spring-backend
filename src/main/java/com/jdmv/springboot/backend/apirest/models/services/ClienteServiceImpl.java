package com.jdmv.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdmv.springboot.backend.apirest.models.dao.IClienteDao;
import com.jdmv.springboot.backend.apirest.models.entity.Cliente;
import com.jdmv.springboot.backend.apirest.models.entity.Region;

@Service //marks the class as a component of service inside spring context
public class ClienteServiceImpl implements IClienteService{

	@Autowired //injects Dao client
	private IClienteDao clienteDao;

	@Override
	@Transactional(readOnly = true) //overwrites the transactionality of the DAO class NOTE: if a custom method is created in the DAO class there must be the this annotation and not here. 
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) { 
		return clienteDao.save(cliente);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> Regions() {
		return clienteDao.Regions();
	}
	
	
}
