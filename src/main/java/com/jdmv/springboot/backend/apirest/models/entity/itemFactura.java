package com.jdmv.springboot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "facturas_items")
public class itemFactura implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name="producto_id") optional, by default will do in this way
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto producto;
	
	public Double getAmount() {
		return this.cantidad.doubleValue() * producto.getPrecio();
	}
	private static final long serialVersionUID = 1L;
	

}
