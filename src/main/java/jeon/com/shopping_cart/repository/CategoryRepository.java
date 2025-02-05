package jeon.com.shopping_cart.repository;

import jeon.com.shopping_cart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);
}
