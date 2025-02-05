package jeon.com.shopping_cart.service.product;

import jeon.com.shopping_cart.model.Product;
import jeon.com.shopping_cart.request.AddProductRequest;
import jeon.com.shopping_cart.request.ProductUpdateRequest;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;

import java.util.List;

public interface IProductService {

    Product add(AddProductRequest p);

    List<Product> all();

    Product find(int id);

    void delete(int id);

    Product update(ProductUpdateRequest p, int id);

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

}
