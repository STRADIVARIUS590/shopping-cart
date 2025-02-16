package jeon.com.shopping_cart.controller;

import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Product;
import jeon.com.shopping_cart.request.AddProductRequest;
import jeon.com.shopping_cart.request.ProductUpdateRequest;
import jeon.com.shopping_cart.response.ApiResponse;
import jeon.com.shopping_cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.data.util.NullableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

@RequestMapping("${api.prefix}/products")
@RestController
@RequiredArgsConstructor

public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index() {
        try {
            List<Product> products = this.productService.all();
            return ResponseEntity.ok().body(new ApiResponse("Registro consultao correctamente", products));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable int id) {
        try {
            Product product = this.productService.find(id);
            return ResponseEntity.ok(new ApiResponse("success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> store(@RequestBody AddProductRequest productRequest) {
        try {
            Product stored_product = this.productService.add(productRequest);
            return ResponseEntity.ok(new ApiResponse("Add product success", stored_product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable int id, @RequestBody ProductUpdateRequest product_request) {
        try {
            Product product = this.productService.update(product_request, id);
            return ResponseEntity.ok(new ApiResponse("Update success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        try {
            Product product = this.productService.find(id);

            if (product != null) {
                this.productService.delete(id);
            }
            return ResponseEntity.ok(new ApiResponse("Eliminado", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> list = this.productService.getProductByBrandAndName(brandName, productName);
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No se encontrator productos", null));
            }
            return ResponseEntity.ok(new ApiResponse("Registros consultados correctamente", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndName(@PathVariable String categoryName, @PathVariable String brandName) {
        try {
            List<Product> list = this.productService.getProductByCategoryAndBrand(categoryName, brandName);
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No se encontraron productos", null));
            }
            return ResponseEntity.ok(new ApiResponse("Registros consultados correctamente", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name)
    {
        try {
            List<Product> list = this.productService.getProductsByName(name);
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No se encontrator productos", null));
            }
            return ResponseEntity.ok(new ApiResponse("Registros consultados correctamente", list));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse> getByCategory(@PathVariable String name)
    {
        try{
            List<Product> list = this.productService.getProductsByCategory(name);
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("no se encontrator productos", null));
            }
            return ResponseEntity.ok(new ApiResponse("Registros consultados correctamente", list));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("by-brand")
    public ResponseEntity<ApiResponse> getByBrand(@PathVariable String name)
    {
        try {
            List list = this.productService.getProductsByBrand(name);

            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No se encontrator productos", null));
            }
            return ResponseEntity.ok(new ApiResponse("Registros consultados correctamente", list));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
  }