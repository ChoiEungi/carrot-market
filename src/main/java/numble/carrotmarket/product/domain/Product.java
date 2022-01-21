package numble.carrotmarket.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.common.BaseEntity;
import numble.carrotmarket.exception.CustomException;
import numble.carrotmarket.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int price;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int interestedProductCount = 0;

    private int commentCount = 0;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    List<InterestProduct> interestedProducts = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Product(String title, int price, String content, ProductState productState, Category category, List<ProductImage> productImages, User user) {
        this(null, title, price, content, productState, category, productImages, user);
    }

    private Product(Long id, String title, int price, String content, ProductState productState, Category category, List<ProductImage> productImages, User user) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.content = content;
        this.productState = productState;
        this.category = category;
        this.productImages = productImages;
        this.user = user;
    }

    public void addCommentCount() {
        this.commentCount++;
    }

    public void deleteCommentCount() {
        this.commentCount--;
    }

    public InterestProduct addInterestedProduct(Long userId) {
        for (InterestProduct interestedProduct : interestedProducts) {
            if (interestedProduct.isInterestedBy(userId)) {
                throw new CustomException("이미 등록한 관심 상품입니다.");
            }
        }
        this.interestedProductCount++;
        return new InterestProduct(userId, this);
    }

    public InterestProduct deleteInterestedProduct(Long userId) {
        for (InterestProduct interestedProduct : interestedProducts) {
            if (interestedProduct.isInterestedBy(userId)) {
                this.interestedProductCount--;
                return interestedProduct;
            }
        }
        throw new CustomException("등록하지 않은 관심상품입니다.");
    }
}
