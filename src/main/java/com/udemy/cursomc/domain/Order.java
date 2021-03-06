package com.udemy.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="ORDERS")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date creationDate;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="order")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="delivery_address_id")
	private Address deliveryAddress;
	
	@OneToMany(mappedBy="id.order")
	private Set<Item> itens = new HashSet<Item>();
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(Integer id, Date creationDate, Customer customer, Address deliveryAddress) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.customer = customer;
		this.deliveryAddress = deliveryAddress;
	}
	
	public double getTotal() {
		double total = 0.0;
		
		for(Item item : itens) {
			total += item.getSubTotal();
		}
		
		return total;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public Set<Item> getItens() {
		return itens;
	}

	public void setItens(Set<Item> itens) {
		this.itens = itens;
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder
		.append("Pedido número: ")
		.append(getId())
		.append(", Data: ")
		.append(sdf.format(getCreationDate()))
		.append(", Cliente: ")
		.append(getCustomer().getName())
		.append(", Situação do Pagamento: ")
		.append(getPayment().getState().getDescription())
		.append("\nDetalhes:\n");
		
		for(Item item: getItens()) {
			builder.append(item.toString());
		}
		
		builder.append("\nValor Total: ")
		.append(nf.format(getTotal()));
		
		return builder.toString();
	}
	
	
}
