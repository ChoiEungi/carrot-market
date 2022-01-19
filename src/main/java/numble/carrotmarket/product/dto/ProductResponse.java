package numble.carrotmarket.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.product.domain.Category;
import numble.carrotmarket.product.domain.Product;
import numble.carrotmarket.product.domain.ProductImage;
import numble.carrotmarket.product.domain.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getTitle(),
                product.getPrice(),
                product.getContent(),
                product.getProductState(),
                product.getCategory(),
                product.getProductImages());

    }

    public static Page<ProductResponse> pageOf(Page<Product> products) {
        return new PageImpl<>(products.stream()
                .map(s -> of(s))
                .collect(Collectors.toList()));

    }




}
