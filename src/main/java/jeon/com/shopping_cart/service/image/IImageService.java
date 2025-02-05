package jeon.com.shopping_cart.service.image;

import jeon.com.shopping_cart.dto.ImageDto;
import jeon.com.shopping_cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image find(int id);

    void delete(int id);

    List<ImageDto> saveImages(List<MultipartFile> images, int product_id);

    void update(MultipartFile image, int image_id);

}
