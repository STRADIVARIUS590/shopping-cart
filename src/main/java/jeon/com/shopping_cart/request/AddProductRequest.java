package jeon.com.shopping_cart.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jeon.com.shopping_cart.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AddProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int stock;
    private Category category;
}
