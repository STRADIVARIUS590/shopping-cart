package jeon.com.shopping_cart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jeon.com.shopping_cart.service.cart.CartItemService;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
            //@JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

// hanokdsad
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public void setTotalPrice()
    {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(this.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Use only the ID for hashCode, not the collection
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartItem cart = (CartItem) obj;
        return Objects.equals(id, cart.id);  // Use only ID for equals
    }


}
