package jeon.com.shopping_cart.controller;

import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Cart;
import jeon.com.shopping_cart.response.ApiResponse;
import jeon.com.shopping_cart.service.cart.ICartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> find(@PathVariable Long id)
    {
        try {
            Cart cart = this.cartService.find(id);
            return ResponseEntity.ok(new ApiResponse("ok", cart));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id){
        try {
            this.cartService.clearCart(id);
            return ResponseEntity.ok(new ApiResponse("clear cart success", null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null ));
        }
    }

    @GetMapping("/total/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId)
    {
        try{
            BigDecimal amount = this.cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Registro consultado correctamente",
                    Map.of("amount", amount)));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
