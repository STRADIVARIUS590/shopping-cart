package jeon.com.shopping_cart.service.category;

import jeon.com.shopping_cart.exception.AlreadyExistsException;
import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Category;
import jeon.com.shopping_cart.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryService implements ICategoryService{
    private final ICategoryRepository categoryRepository;

    @Override
    public Category add(Category category_request) {
        return Optional.of(category_request).filter(category ->
                !this.categoryRepository.existsByName(category_request.getName()))
                .map(category -> this.categoryRepository.save(category))
                .orElseThrow(() -> new AlreadyExistsException("La categoria ya existe: " + category_request.getName()));
    }

    @Override
    public List<Category> all() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category find(int id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Category Found ")) ;
    }

    @Override
    public void delete(int id) {
        this.categoryRepository.findById(id)
                .ifPresentOrElse(category -> this.categoryRepository.delete(category),
                        () -> { throw new ResourceNotFoundException("Categoria no encontrada");});
    }

    @Override
    public Category update(Category request, int id) {
        return Optional.ofNullable(this.find(id))
                .map(old_category -> {
                    old_category.setName(request.getName());
                    return this.categoryRepository.save(old_category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return this.categoryRepository.findByName(name);
    }

}
