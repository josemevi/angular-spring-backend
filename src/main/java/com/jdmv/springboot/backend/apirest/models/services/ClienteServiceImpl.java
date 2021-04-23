package com.jdmv.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdmv.springboot.backend.apirest.models.dao.IClienteDao;
import com.jdmv.springboot.backend.apirest.models.dao.IFacturaDao;
import com.jdmv.springboot.backend.apirest.models.dao.IProductoDao;
import com.jdmv.springboot.backend.apirest.models.entity.Cliente;
import com.jdmv.springboot.backend.apirest.models.entity.Factura;
import com.jdmv.springboot.backend.apirest.models.entity.Producto;
import com.jdmv.springboot.backend.apirest.models.entity.Region;

@Service //marks the class as a component of service inside spring context
public class ClienteServiceImpl implements IClienteService{

	@Autowired //injects Dao client
	private IClienteDao clienteDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Autowired
	private IProductoDao productoDao;

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

	//findById is a custom method that can dynamically change depending on some writing rules
	//for example we can also have findByEmail if ther's a email field in the model (Jpa query creation)
	//.orElse means that is going to return null
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
	
	//Instead of the .orElse there's others methods like .get that returns a object
	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaDao.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturabyId(Long id) {
		facturaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String term) {
		return productoDao.findByNombre(term);
	}
	
	
}
