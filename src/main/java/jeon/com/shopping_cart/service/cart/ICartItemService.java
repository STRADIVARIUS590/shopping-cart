package jeon.com.shopping_cart.service.cart;

import jeon.com.shopping_cart.model.Cart;
import jeon.com.shopping_cart.model.CartItem;

public interface ICartItemService {
    <T> T addCartItem(Long cardId, int productId, int quantity);
    <T> T removeCartItem(Long cartId, int productId);
    CartItem getCartItem(Long cartId, int productId);
    <T> T updateItemQuantity(Long cartId, int productId, int quantity);
}
