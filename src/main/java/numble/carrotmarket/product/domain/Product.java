package numble.carrotmarket.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.product.dto.ProductResponse;
import numble.carrotmarket.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {

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

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<ProductImage> productImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
