package com.jdmv.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {
	
	
	
	public Factura() {
	this.items = new ArrayList<>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private String observation;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@JsonIgnoreProperties({"facturas","hibernateLazyInitializer","handler"})
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn(name = "factura_id") //required to specify if we don't create a factura field inside itemFactura clas, the bd field will be created at item_factura table
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private List<itemFactura> items;
	
	@PrePersist
	public void preLoadDate() {
		this.createAt = new Date();
	}
	
	public Double getTotal() {
		Double total = 0.00;
		for(itemFactura item: items) {
			total += item.getAmount();
		}
		return total;
	}

	private static final long serialVersionUID = 1L;

}
