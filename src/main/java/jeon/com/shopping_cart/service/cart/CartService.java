package jeon.com.shopping_cart.service.cart;

import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Cart;
import jeon.com.shopping_cart.repository.ICartItemRepository;
import jeon.com.shopping_cart.repository.ICartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor

public class CartService implements ICartService{
    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    // private final AtomicInteger cartIdGenerator = new AtomicInteger(1);

    @Override
    public Cart find(Long id) {
        Cart cart = this.cartRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("No encontrado"));

        // ?? no se por que hizo esto
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return this.cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = this.find(id);
        this.cartItemRepository.deleteAllByCartId(cart.getId());
        cart.getItems().clear();
        this.cartRepository.deleteById((long) id);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = this.find(cartId);
        return cart.getTotalAmount();
//        return cart.getItems().stream()
//                .map(CartItem::getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long initializeNewCart()
    {
        Cart newCart = new Cart();
        newCart.setTotalAmount(BigDecimal.ZERO);
        return (this.cartRepository.save(newCart)).getId();
    // return newCartId;
    }
}
