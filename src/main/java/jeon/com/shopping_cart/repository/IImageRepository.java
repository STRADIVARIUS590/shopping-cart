package jeon.com.shopping_cart.repository;

import jeon.com.shopping_cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Integer> {


}
