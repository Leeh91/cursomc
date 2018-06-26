package com.udemy.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udemy.cursomc.domain.Address;
import com.udemy.cursomc.domain.CardPayment;
import com.udemy.cursomc.domain.Category;
import com.udemy.cursomc.domain.City;
import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.domain.Item;
import com.udemy.cursomc.domain.Order;
import com.udemy.cursomc.domain.Payment;
import com.udemy.cursomc.domain.Product;
import com.udemy.cursomc.domain.State;
import com.udemy.cursomc.domain.TicketPayment;
import com.udemy.cursomc.domain.enums.CustomerType;
import com.udemy.cursomc.domain.enums.StatePayment;
import com.udemy.cursomc.repositories.AddressRepository;
import com.udemy.cursomc.repositories.CategoryRepository;
import com.udemy.cursomc.repositories.CityRepository;
import com.udemy.cursomc.repositories.CustomerRepository;
import com.udemy.cursomc.repositories.ItemRepository;
import com.udemy.cursomc.repositories.OrderRepository;
import com.udemy.cursomc.repositories.PaymentRepository;
import com.udemy.cursomc.repositories.ProductRepository;
import com.udemy.cursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category category1 = new Category(null, "Informática");
		Category category2 = new Category(null, "Escritório");
		
		Product product1 = new Product(null, "Computador", 2000.00);
		Product product2 = new Product(null, "Impressora", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);
		
		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2));
		
		product1.getCategories().addAll(Arrays.asList(category1));
		product2.getCategories().addAll(Arrays.asList(category1, category2));
		product3.getCategories().addAll(Arrays.asList(category1));
		
		this.categoryRepository.saveAll(Arrays.asList(category1, category2));
		this.productRepository.saveAll(Arrays.asList(product1, product2, product3));
		
		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");
		
		City city1 = new City(null, "Uberlândia", state1);
		City city2 = new City(null, "São Paulo", state2);
		City city3 = new City(null, "Campinas", state2);
		
		state1.getCities().addAll(Arrays.asList(city1));
		state2.getCities().addAll(Arrays.asList(city2, city3));
		
		this.stateRepository.saveAll(Arrays.asList(state1, state2));
		this.cityRepository.saveAll(Arrays.asList(city1, city2, city3));
		
		Customer customer1 = new Customer(null, "Maria Silva", "maria@gmail.com", "09193120044", CustomerType.INDIVIDUAL);
		
		customer1.getTelefones().addAll(Arrays.asList("44027598", "953869061"));
		
		Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "2024876", customer1, city1);
		Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "12954345", customer1, city2);
		
		customer1.getAddresses().addAll(Arrays.asList(address1, address2));
		
		this.customerRepository.saveAll(Arrays.asList(customer1));
		this.addressRepository.saveAll(Arrays.asList(address1, address2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Order order1 = new Order(null, sdf.parse("30/09/2017 10:32"), customer1, address1);
		Order order2 = new Order(null, sdf.parse("10/10/2017 19:35"), customer1, address2);
		
		Payment payment1 = new CardPayment(null, StatePayment.SETTLED, order1, 6);
		order1.setPayment(payment1);
		
		Payment payment2 = new TicketPayment(null, StatePayment.PENDING, order2, sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(payment2);
		
		customer1.getOrders().addAll(Arrays.asList(order1, order2));
		
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(payment1, payment2));
		
		Item item1 = new Item(order1, product1, 0.00, 1, 2000.00);
		Item item2 = new Item(order1, product3, 0.00, 2, 80.00);
		Item item3 = new Item(order2, product2, 100.00, 1, 800.00);
		
		order1.getItens().addAll(Arrays.asList(item1, item2));
		order2.getItens().addAll(Arrays.asList(item3));
		
		product1.getItens().addAll(Arrays.asList(item1));
		product2.getItens().addAll(Arrays.asList(item3));
		product3.getItens().addAll(Arrays.asList(item2));
		
		this.itemRepository.saveAll(Arrays.asList(item1, item2, item3));
	}
}
