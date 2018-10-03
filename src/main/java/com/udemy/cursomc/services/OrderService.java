package com.udemy.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.udemy.cursomc.domain.Customer;
import com.udemy.cursomc.domain.Item;
import com.udemy.cursomc.domain.Order;
import com.udemy.cursomc.domain.TicketPayment;
import com.udemy.cursomc.domain.enums.StatePayment;
import com.udemy.cursomc.repositories.ItemRepository;
import com.udemy.cursomc.repositories.OrderRepository;
import com.udemy.cursomc.repositories.PaymentRepository;
import com.udemy.cursomc.resources.exception.AuthorizationException;
import com.udemy.cursomc.resources.exception.ObjectNotFoundException;
import com.udemy.cursomc.security.UserSS;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private IEmailService emailService;
	
	public Order find(Integer id) {
		Optional<Order> order = this.orderRepository.findById(id);
		return order.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Order.class.getName()));
	}
	
	public Order insert(Order order) {
		order.setId(null);
		order.setCreationDate(new Date());
		order.setCustomer(this.customerService.getCustomer(order.getCustomer().getId()));
		order.getPayment().setState(StatePayment.PENDING);
		order.getPayment().setOrder(order);
		
		if(order.getPayment() instanceof TicketPayment) {
			TicketPayment payment = (TicketPayment) order.getPayment();
			this.ticketService.fillPaymentTicket(payment, order.getCreationDate());
		}
		
		order = this.orderRepository.save(order);
		this.paymentRepository.save(order.getPayment());
		
		for(Item item : order.getItens()) {
			item.setDiscount(0.0);
			item.setProduct(this.productService.find(item.getProduct().getId()));
			item.setPrice(this.productService.find(item.getProduct().getId()).getPrice());
			item.setOrder(order);
		}
		
		itemRepository.saveAll(order.getItens());
		
		this.emailService.sendOrderConfirmationHtmlEmail(order);
		
		return order;
	}
	
	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Customer customer = this.customerService.getCustomer(user.getId());
		
		return this.orderRepository.findByCustomer(customer, pageRequest);
	}

}
