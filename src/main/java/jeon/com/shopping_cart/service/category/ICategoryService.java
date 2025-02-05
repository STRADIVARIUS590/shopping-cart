package jeon.com.shopping_cart.service.category;

import jeon.com.shopping_cart.model.Category;

import java.util.List;

public interface ICategoryService {

    Category add(Category c);

    List<Category> all();

    Category find(int id);

    void delete(int id);

    Category update(Category c, int id);

    Category getCategoryByName(String name);



}
