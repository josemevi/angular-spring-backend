package com.jdmv.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//Good practice tip: always implement Serializable interface inside the entity class this make available to convert data from java object to json, and also save the data in a http session 
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(unique=true, length=20)
	private String username;
	
	@Column(length=60)
	private String password;
	
	private Boolean enabled;
	
	private String nombre;
	private String apellido;
	
	@Column(length=20)
	private String email;
	
	//creates a many to many table 
	//by default is going to use the sql best practice to name the table
	//we can custom this using @JoinTable with name, joinColumn etc..
	//This has to be done in the table that owns the many to many relation.
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="rol_id"))
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name="usuario_id"), 
	inverseJoinColumns = @JoinColumn(name="role_id"),
	uniqueConstraints = {@UniqueConstraint(columnNames= {"usuario_id","role_id"})})
	private List<Role> roles;
	

	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
