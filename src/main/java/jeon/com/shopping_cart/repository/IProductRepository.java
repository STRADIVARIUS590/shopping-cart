package jeon.com.shopping_cart.repository;

import jeon.com.shopping_cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrandName(String brand);

    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);
}
