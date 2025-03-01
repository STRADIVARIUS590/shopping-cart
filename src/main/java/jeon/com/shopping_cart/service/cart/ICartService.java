package jeon.com.shopping_cart.service.cart;

import jeon.com.shopping_cart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart find(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
}
