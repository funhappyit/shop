package com.example.shop.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.dto.OrderDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.Member;
import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import com.example.shop.repository.OrderRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	MemberRepository memberRepository;

	public Item saveItem(){
		Item item = new Item();
		item.setItemNm("테스트 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		return itemRepository.save(item);
	}

	public Member saveMember(){
		Member member = new Member();
		member.setEmail("test@test.com");
		return memberRepository.save(member);
	}

	@Test
	@DisplayName("주문 테스트")
	public void order(){
		Item item = saveItem();
		Member member = saveMember();

		OrderDto orderDto = new OrderDto();
		orderDto.setCount(10);
		orderDto.setItemId(item.getId());

		Long orderId = orderService.order(orderDto,member.getEmail());

		Order order = orderRepository.findById(orderId)
			.orElseThrow(EntityExistsException::new);

		List<OrderItem> orderItems = order.getOrderItems();
		int totalPrice = orderDto.getCount()*item.getPrice();
		assertEquals(totalPrice,order.getTotalPrice());

	}


}
