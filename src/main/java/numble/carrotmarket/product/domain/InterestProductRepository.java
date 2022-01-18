package numble.carrotmarket.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

}
