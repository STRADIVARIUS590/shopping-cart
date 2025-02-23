package jeon.com.shopping_cart.repository;

import jeon.com.shopping_cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByProductId(int id);
}
