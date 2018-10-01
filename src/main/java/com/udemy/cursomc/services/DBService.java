package com.udemy.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.udemy.cursomc.domain.enums.Profile;
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

@Service
public class DBService {
	
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
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void instantiateTestDatabase() throws ParseException {
		Category category1 = new Category(null, "Informática");
		Category category2 = new Category(null, "Escritório");
		Category category3 = new Category(null, "Cama, mesa e banho");
		Category category4 = new Category(null, "Eletrônicos");
		Category category5 = new Category(null, "Jardinagem");
		Category category6 = new Category(null, "Decoração");
		Category category7 = new Category(null, "Perfumaria");

		
		Product product1 = new Product(null, "Computador", 2000.00);
		Product product2 = new Product(null, "Impressora", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);
		Product product4 = new Product(null, "Mesa de escritorio", 300.00);
		Product product5 = new Product(null, "Toalha", 50.00);
		Product product6 = new Product(null, "Colcha", 200.00);
		Product product7 = new Product(null, "TV true color", 1200.00);
		Product product8 = new Product(null, "Roçadeira", 800.00);
		Product product9 = new Product(null, "Abajour", 100.00);
		Product product10 = new Product(null, "Pendente", 180.00);
		Product product11 = new Product(null, "Shampoo", 90.00);
		
		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2, product4));
		category3.getProducts().addAll(Arrays.asList(product5, product6));
		category4.getProducts().addAll(Arrays.asList(product1, product2, product3, product7));
		category5.getProducts().addAll(Arrays.asList(product8));
		category6.getProducts().addAll(Arrays.asList(product9, product10));
		category7.getProducts().addAll(Arrays.asList(product11));
		
		product1.getCategories().addAll(Arrays.asList(category1, category4));
		product2.getCategories().addAll(Arrays.asList(category1, category2, category4));
		product3.getCategories().addAll(Arrays.asList(category1, category4));
		product4.getCategories().addAll(Arrays.asList(category2));
		product5.getCategories().addAll(Arrays.asList(category3));
		product6.getCategories().addAll(Arrays.asList(category3));
		product7.getCategories().addAll(Arrays.asList(category4));
		product8.getCategories().addAll(Arrays.asList(category5));
		product9.getCategories().addAll(Arrays.asList(category6));
		product10.getCategories().addAll(Arrays.asList(category6));
		product11.getCategories().addAll(Arrays.asList(category7));
		
		this.categoryRepository
				.saveAll(Arrays.asList(category1, category2, category3, category4, category5, category6, category7));
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
		
		Customer customer1 = new Customer(null, "Maria Silva", "developer.lambdacode@gmail.com", "09193120044", CustomerType.INDIVIDUAL, bCryptPasswordEncoder.encode("123"));
		customer1.getPhones().addAll(Arrays.asList("44027598", "953869061"));
		
		Customer customer2 = new Customer(null, "Ana Costa", "developer2.lambdacode@gmail.com", "75098735092", CustomerType.INDIVIDUAL, bCryptPasswordEncoder.encode("321"));
		customer2.getPhones().addAll(Arrays.asList("44119087", "987654321"));
		customer2.addProfile(Profile.ADMIN);
		
		Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "2024876", customer1, city1);
		Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "12954345", customer1, city2);
		Address address3 = new Address(null, "Avenida Floriano", "2106", null, "Centro", "12940000", customer2, city2);
		
		customer1.getAddresses().addAll(Arrays.asList(address1, address2));
		customer2.getAddresses().addAll(Arrays.asList(address3));
		
		this.customerRepository.saveAll(Arrays.asList(customer1, customer2));
		this.addressRepository.saveAll(Arrays.asList(address1, address2, address3));
		
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
