package jeon.com.shopping_cart.dto;

import jakarta.persistence.*;
import jeon.com.shopping_cart.model.Category;
import jeon.com.shopping_cart.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProductDto {
    private int id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int stock;
    private Category category;
    private List<ImageDto> images;
}
