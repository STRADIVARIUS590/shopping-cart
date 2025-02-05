package jeon.com.shopping_cart.service.image;

import jeon.com.shopping_cart.dto.ImageDto;
import jeon.com.shopping_cart.exception.ResourceNotFoundException;
import jeon.com.shopping_cart.model.Image;
import jeon.com.shopping_cart.model.Product;
import jeon.com.shopping_cart.repository.IImageRepository;
import jeon.com.shopping_cart.repository.IProductRepository;
import jeon.com.shopping_cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final IImageRepository imageRepository;
    private final IProductService productService;


    @Override
    public Image find(int id) {
        return this.imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
    }

    @Override
    public void delete(int id) {
        this.imageRepository.findById(id).ifPresentOrElse(image -> this.imageRepository.delete(image),
                () -> {throw new ResourceNotFoundException("No se a encontrado la imagen a borrar"); });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> images, int product_id) {
        Product product = this.productService.find(product_id);

        List<ImageDto> savedImagesDtos = new ArrayList<>();
        for(MultipartFile file: images)
        {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileName(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadBasePath = "/api/v1/images/download/";
                String downloadUrl = downloadBasePath + image.getId();
                image.setUrl(downloadUrl);

                Image savedImage = this.imageRepository.save(image);
                savedImage.setUrl(downloadBasePath + savedImage.getId());

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getUrl());

                savedImagesDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImagesDtos;


    }

    @Override
    public void update(MultipartFile file, int image_id) {
        Image image = this.find(image_id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            this.imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
