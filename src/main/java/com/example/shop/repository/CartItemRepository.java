package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
	CartItem findByCartIdAndItemId(Long cartId,Long itemId);
}
