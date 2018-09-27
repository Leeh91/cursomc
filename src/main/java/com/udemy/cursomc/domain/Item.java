package com.udemy.cursomc.domain;

import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item {
	
	@JsonIgnore
	@EmbeddedId
	private ItemPK id = new ItemPK();
	
	private Double discount;
	private Integer amount;
	private Double price;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	public Item(Order order, Product product, Double discount, Integer amount, Double price) {
		super();
		
		this.id.setOrder(order);
		this.id.setProduct(product);
		
		this.discount = discount;
		this.amount = amount;
		this.price = price;
	}
	
	public double getSubTotal() {
		return (this.price - this.discount) * this.amount;
	}
	
	@JsonIgnore
	public Order getOrder() {
		return this.id.getOrder();
	}
	
	public void setOrder(Order order) {
		this.id.setOrder(order);
	}
	
	public Product getProduct() {
		return this.id.getProduct();	
	}
	
	public void setProduct(Product product) {
		this.id.setProduct(product);
	}

	public ItemPK getId() {
		return id;
	}

	public void setId(ItemPK id) {
		this.id = id;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		Item other = (Item) obj;
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
		StringBuilder builder = new StringBuilder();
		builder.append(getProduct().getName())
		.append(", Quantidade: ")
		.append(getAmount())
		.append(", Preço unitário: ")
		.append(nf.format(getPrice()))
		.append(", Subtotal: ")
		.append(nf.format(getSubTotal()))
		.append("\n");
		return builder.toString();
	}
	
}
