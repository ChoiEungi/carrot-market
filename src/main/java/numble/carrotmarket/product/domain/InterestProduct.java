package numble.carrotmarket.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.user.User;

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

    public boolean isInterestedIn() {
        return product.getId().equals(this.userId);
    }

}
