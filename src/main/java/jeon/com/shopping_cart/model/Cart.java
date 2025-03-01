package jeon.com.shopping_cart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CartItem> items = new HashSet<>();

    public void addItem(CartItem item)
    {
        this.items.add(item);
        item.setCart(this);
        this.updateTotalAmount();
    }

    public void removeItem(CartItem item)
    {
        this.items.remove(item);
        item.setCart(null);
        this.updateTotalAmount();
    }

//    public void updateTotalAmount()
//    {
//        System.out.println("START TOTAL AMOUNT");
//        BigDecimal totalAmount = items.stream().map(
//                item -> {
//                    BigDecimal unitPrice = item.getUnitPrice();
//                    if(unitPrice == null){
//                        return BigDecimal.ZERO;
//                    }
//                    return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
//                }).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        System.out.println("END TOTAL AMOUNT CHECK  " + totalAmount );
//        this.setTotalAmount(totalAmount);
//    }

    private void updateTotalAmount() {
        this.totalAmount = items.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return  BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);  // Use only the ID for hashCode, not the collection
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cart cart = (Cart) obj;
        return Objects.equals(id, cart.id);  // Use only ID for equals
    }

}

