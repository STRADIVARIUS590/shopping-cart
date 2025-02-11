package jeon.com.shopping_cart.controller;

import jakarta.annotation.Resource;
import jeon.com.shopping_cart.exception.AlreadyExistsException;
import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Category;
import jeon.com.shopping_cart.response.ApiResponse;
import jeon.com.shopping_cart.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index()
    {
        try {
            List<Category> categories = this.categoryService.all();
            return ResponseEntity.ok().body(new ApiResponse("Registro consultado correctamente", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> store(@RequestBody Category category) {
        try {

            Category stored_category = this.categoryService.add(category);
            return ResponseEntity.ok(new ApiResponse("success", stored_category));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse( e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable int id)
    {
        try {
            Category category = this.categoryService.find(id);
            return ResponseEntity.ok(new ApiResponse("success", category));
        }catch (ResourceNotFoundException e)
        {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<ApiResponse> getByName(@PathVariable String name)
    {
        try {
            Category category = this.categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("success", category));
        }catch (ResourceNotFoundException e)
        {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        try {
            Category category = this.categoryService.find(id);
            if(category != null){
                this.categoryService.delete(id);
            }
        }catch(ResourceNotFoundException e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable int id, @RequestBody Category category)
    {
        try {
            Category updatedCategory = this.categoryService.update(category, id);

            return ResponseEntity.ok(new ApiResponse("update successful", updatedCategory));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
      //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
