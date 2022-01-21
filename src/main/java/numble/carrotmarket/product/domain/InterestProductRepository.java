package numble.carrotmarket.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

    Optional<List<InterestProduct>> findInterestProductsByUserId(Long userId);

    Optional<List<InterestProduct>> findInterestProductsByProductId(Long productId);

}
