package numble.carrotmarket.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.product.domain.Category;
import numble.carrotmarket.product.domain.Product;
import numble.carrotmarket.product.domain.ProductImage;
import numble.carrotmarket.product.domain.ProductState;
import numble.carrotmarket.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductRequest {
    private String title;
    private int price;
    private String content;
    private ProductState productState;
    private Category category;
    List<MultipartFile> productImages;

    public ProductRequest(String title, int price, String content, ProductState productState, Category category, List<MultipartFile> productImages) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.productState = productState;
        this.category = category;
        this.productImages = productImages;
    }

    public Product toEntity(List<ProductImage> productImages, User user){
        return new Product(title, price, content, productState, category, productImages, user);
    }
}
