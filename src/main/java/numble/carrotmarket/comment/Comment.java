package numble.carrotmarket.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.common.BaseEntity;
import numble.carrotmarket.product.domain.Product;
import numble.carrotmarket.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String description, Product product, User user) {
        this(null, description, product, user);
    }

    private Comment(Long id, String description, Product product, User user) {
        this.id = id;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public void updateComment(String description) {
        this.description = description;
    }
}
