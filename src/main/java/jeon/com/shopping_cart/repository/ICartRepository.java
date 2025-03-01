package jeon.com.shopping_cart.repository;

import jeon.com.shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> { }
