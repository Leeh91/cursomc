package com.udemy.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.cursomc.domain.enums.CustomerType;

@Entity
public class Customer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String individualOrPartyDoc;
	private Integer type;
	
	@OneToMany(mappedBy="customer")
	private List<Address> addresses = new ArrayList<Address>();
	
	@ElementCollection
	@CollectionTable(name="PHONES")
	private Set<String> phones = new HashSet<String>();
	
	@JsonIgnore
	@OneToMany(mappedBy="customer")
	private List<Order> orders = new ArrayList<Order>();
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(Integer id, String name, String email, String individualOrPartyDoc, CustomerType type) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.individualOrPartyDoc = individualOrPartyDoc;
		this.type = (type == null) ? null : type.getCode();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndividualOrPartyDoc() {
		return individualOrPartyDoc;
	}

	public void setIndividualOrPartyDoc(String individualOrPartyDoc) {
		this.individualOrPartyDoc = individualOrPartyDoc;
	}

	public CustomerType getType() {
		return CustomerType.toEnum(this.type);
	}

	public void setType(CustomerType type) {
		this.type = type.getCode();
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
