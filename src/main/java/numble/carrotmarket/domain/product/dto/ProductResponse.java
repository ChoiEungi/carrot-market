package numble.carrotmarket.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.domain.product.domain.Category;
import numble.carrotmarket.domain.product.domain.Product;
import numble.carrotmarket.domain.product.domain.ProductImage;
import numble.carrotmarket.domain.product.domain.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private String title;
    private int price;
    private String content;
    private ProductState productState;
    private Category category;
    private List<ProductImage> productImages;
    private String createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getTitle(),
                product.getPrice(),
                product.getContent(),
                product.getProductState(),
                product.getCategory(),
                product.getProductImages(),
                product.getformattedDate(LocalDateTime.now()),
                product.getUpdatedAt()
        );
    }

    public static Page<ProductResponse> pageOf(Page<Product> products) {
        return new PageImpl<>(products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList()));

    }


}
