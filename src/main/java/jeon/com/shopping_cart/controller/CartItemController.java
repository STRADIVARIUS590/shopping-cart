package jeon.com.shopping_cart.controller;

import jeon.com.shopping_cart.model.Cart;
import jeon.com.shopping_cart.repository.ICartRepository;
import jeon.com.shopping_cart.response.ApiResponse;
import jeon.com.shopping_cart.service.cart.ICartItemService;
import jeon.com.shopping_cart.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final ICartItemService cardItemService;
    private final ICartService cartService;
    @PostMapping("/add-item-to-cart")
    private ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                      @RequestParam int productId,
                                                      @RequestParam int quantity)


    {
        // return ResponseEntity.ok(new ApiResponse("", cartId = this.cartService.initializeNewCart()));

        try{
            if(cartId == null){
                cartId = this.cartService.initializeNewCart();
            }
            var cart = this.cardItemService.addCartItem(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("item added to cart successfully", cart));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/remove-item-from-cart/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable int productId)
    {
        try{
            var t = this.cardItemService.removeCartItem(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Eliminao", t));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable int itemId,
                                                          @RequestParam int quantity)
    {
        try{
            var t = this.cardItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Actualizado corectamente", t));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
