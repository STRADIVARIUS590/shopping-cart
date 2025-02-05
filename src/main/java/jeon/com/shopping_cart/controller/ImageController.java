package jeon.com.shopping_cart.controller;

import jeon.com.shopping_cart.dto.ImageDto;
import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Image;
import jeon.com.shopping_cart.response.ApiResponse;
import jeon.com.shopping_cart.service.image.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/${api.prefix}/images")
@AllArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping(("/upload"))
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> images, @RequestParam int productId)
    {
        try {
            List<ImageDto> savedImages = this.imageService.saveImages(images, productId);

            return ResponseEntity.ok(new ApiResponse("Upload Success", savedImages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/download/{id}")
    public  ResponseEntity<Resource> download(@RequestParam int id) throws SQLException {
        Image image = this.imageService.find(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes( 1 , (int) image.getImage().length()));

        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
    }

    @PutMapping("/image/{id}/update")
    public ResponseEntity<ApiResponse> update(@RequestBody MultipartFile file, @PathVariable int id)
    {
        Image image = this.imageService.find(id);

        if(image != null) {
            try {
                this.imageService.update(file, id);
                return ResponseEntity.ok(new ApiResponse("upate success", image));
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("update failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        try {
            Image image = this.imageService.find(id);
            if(image != null) {
                this.imageService.delete(id);
                return ResponseEntity.ok(new ApiResponse("Delete success", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("delete failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
