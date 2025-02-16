package jeon.com.shopping_cart.service.product;

import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Category;
import jeon.com.shopping_cart.model.Product;
import jeon.com.shopping_cart.repository.ICategoryRepository;
import jeon.com.shopping_cart.repository.IProductRepository;
import jeon.com.shopping_cart.request.AddProductRequest;
import jeon.com.shopping_cart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    public Product add(AddProductRequest request)
    {
        // check if category is found in db
        // true :   set as product category
        // false  : create category
        // set as product category
        Category category = Optional.ofNullable(this.categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return this.categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return this.productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category)
    {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                category
        );
    }

    @Override
    public List<Product> all() {
        return this.productRepository.findAll();
    }

    @Override
    public Product find(int id) {
        return this.productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    @Override
    public void delete(int id) {
        this.productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () ->
                {throw new ResourceNotFoundException("No encontrado");});
        ;
    }

    @Override
    public Product update(ProductUpdateRequest request, int id) {
        return this.productRepository.findById(id)
                .map(existing_product -> this.updateProduct(existing_product, request))
                .map(product -> this.productRepository.save(product))
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

    }

    public Product updateProduct(Product existing_product, ProductUpdateRequest product_request)
    {
        existing_product.setName(product_request.getName());
        existing_product.setBrand(product_request.getBrand());
        existing_product.setDescription(product_request.getDescription());
        existing_product.setPrice(product_request.getPrice());
        existing_product.setStock(product_request.getStock());

        Category category = categoryRepository.findByName(product_request.getCategory().getName());

        existing_product.setCategory(category);
        return existing_product;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return this.productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return this.productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return this.productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return this.productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return this.productRepository.countByBrandAndName(brand, name);
    }
}
