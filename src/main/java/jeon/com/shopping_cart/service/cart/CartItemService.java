package jeon.com.shopping_cart.service.cart;

import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Cart;
import jeon.com.shopping_cart.model.CartItem;
import jeon.com.shopping_cart.model.Product;
import jeon.com.shopping_cart.repository.ICartItemRepository;
import jeon.com.shopping_cart.repository.ICartRepository;
import jeon.com.shopping_cart.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor

public class CartItemService implements ICartItemService {
    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;

//    @Override
//    public void addCartItem(int cardId, int productId, int quantity) {
//        //1 get cart
//        //2 get product
//        //3 check if is already in cart
//        //4 if yes, then increase the quantity with the requested quantity
//        // else add cart item entry
//        Cart cart = this.cartService.find(cardId);
//        Product product = productService.find(productId);
//        System.out.println(cart);
//
//        CartItem cartItem = cart.getItems()
//                .stream()
//                .filter(item -> item.getProduct().getId() == (productId))
//                .findFirst().orElse(new CartItem());
//
//        if(cartItem.getId() <= 0){
//            cartItem.setProduct(product);
//            cartItem.setQuantity(cardId);
//            cartItem.setCart(cart);
//            cartItem.setUnitPrice(product.getPrice());
//        }else{
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        }
//
//        cartItem.setTotalPrice();
//        cart.addItem(cartItem);
//        System.out.println("!!!");
//        System.out.println(cartItem.getCart());
//        System.out.println("poad");
//        this.cartItemRepository.save(cartItem);
//        System.out.println("TA");
//        System.out.println(cart);
//        System.out.println("FFF");
//        this.cartRepository.save(cart);
//    }
    @Override
    public <T> T addCartItem(Long cartId, int productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
        Cart cart = cartService.find(cartId);
        Product product = productService.find(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId() == (productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() <= 0) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
        return (T) cart;
//    return (T) List.of(
//            0, cart,
//            1, cartItem
//    );
    }

    @Override
    public <T> T removeCartItem(Long cartId, int productId) {
        Cart cart = this.cartService.find(cartId);
        CartItem itemToRemove = this.getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        this.cartRepository.save(cart);
        return (T) cart;
    }

    @Override
    public CartItem getCartItem(Long cartId, int productId)
    {
        Cart cart = this.cartService.find(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
    }

    @Override
    public <T>T updateItemQuantity(Long cartId, int productId, int quantity) {
        Cart cart = this.cartService.find(cartId);
        cart.getItems()
                .stream()
                .filter(cartItem ->
                cartItem.getProduct().getId() == productId)
                .findFirst()
                 .ifPresent(cartItem -> {
                     cartItem.setQuantity(quantity);
                     cartItem.setUnitPrice(cartItem.getProduct().getPrice());
                     cartItem.setTotalPrice();
                     this.cartItemRepository.save(cartItem);
                 });

        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
        return (T)cart;
    }
}
