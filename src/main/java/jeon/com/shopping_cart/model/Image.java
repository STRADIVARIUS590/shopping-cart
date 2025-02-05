package jeon.com.shopping_cart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import java.sql.Blob;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String url;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
}