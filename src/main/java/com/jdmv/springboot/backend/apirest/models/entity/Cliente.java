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
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Entity //used to mark the class as a Entity (a table of the db)
@Table(name="clientes") //Specifies the table name inside the db NOTE: can be avoided if the class name is the same as the table name
public class Cliente implements Serializable{
	
	
	//To init the factura list
	public Cliente() {
		this.facturas = new ArrayList<>();
	}

	@Id //specifies the id of the table
	@GeneratedValue(strategy = GenerationType.IDENTITY) //specifies a auto generated value type IDENTITY is the common use for mysql
	private Long id;
	
	//validations rules
	//by default message ill be auto added depending of the locale of the user requesting the data, we can edit the msg adding the "message" attribute tu the annotation
	@NotEmpty(message = "no puede estar vacío")
	@Size(min = 4, max = 12, message="el tamaño debe ser entre 4 y 12 caracteres")
	//@Column() can be skipped if the db column name are the same than the variable names.
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty(message = "no puede estar vacío")
	private String apellido;
	
	@NotEmpty(message = "no puede estar vacío")
	@Email(message="no es una dirección válida")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotNull(message = "no puede estar vacío") 
	@Column(name="create_at")
	@Temporal(TemporalType.DATE) //Used for properties of type Date or Calendar for correct match with Java typos OR for persistent fields 
	private Date createAt;
	
	private String photo;
	
	@NotNull(message = "no puede estar vacío") 
	//We create a FK by simple creating a attribute with the typo of the class and specifying the relation type
	//Note that like all the attributes, this will need the get and set methods
	//fetch will specify when the server is going to load the data
	//the type LAZY will load the data when the method get is called.
	//This means when generating the json with all the clients this will load last and is going to generate a json inside the first one with all the regions
	//under the hood this also is going to create a proxy object that act like a bridge to access data.
	@ManyToOne(fetch=FetchType.LAZY)
	//this's for specify the correspondent table name for the FK by default is going to take the attribute name + "_" +PK (attribute marked with @Id in the other class)
	//can be omitted here 
	@JoinColumn(name="region_id")  
	//By default the fetchType used above (the one creating the object) will generate additional attributes own of the framework
	//We need to exclude this attributes
	//This's not part of the persistence API
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Region region;
	
//	@PrePersist //Specifies a callback method, is a life cycle method of the entity class
//	public void PrePersist() {
//		createAt = new Date();
//	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Factura> facturas;


	private static final long serialVersionUID = 1L;

}
