package numble.carrotmarket.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURL; // imageURL vs imageKey

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private Product product;


}