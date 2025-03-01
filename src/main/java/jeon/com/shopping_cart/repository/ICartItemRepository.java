package jeon.com.shopping_cart.repository;

import jeon.com.shopping_cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Integer> {

    public void deleteAllByCartId(Long id);
}
