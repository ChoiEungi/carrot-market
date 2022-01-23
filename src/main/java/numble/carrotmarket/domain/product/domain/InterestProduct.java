package numble.carrotmarket.domain.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InterestProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public InterestProduct(Long userId, Product product) {
        this.userId = userId;
        this.product = product;
    }

    public boolean isInterestedBy(Long userId){
        return this.userId.equals(userId);
    }
}
