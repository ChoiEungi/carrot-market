package numble.carrotmarket.domain.product;

import lombok.RequiredArgsConstructor;
import numble.carrotmarket.domain.product.domain.InterestProductRepository;
import numble.carrotmarket.domain.product.domain.Product;
import numble.carrotmarket.domain.product.domain.ProductImage;
import numble.carrotmarket.domain.product.domain.ProductRespository;
import numble.carrotmarket.domain.product.dto.ProductResponse;
import numble.carrotmarket.domain.exception.CustomException;
import numble.carrotmarket.domain.product.domain.*;
import numble.carrotmarket.domain.product.dto.ProductRequest;
import numble.carrotmarket.domain.s3api.S3ApiProvider;
import numble.carrotmarket.domain.user.User;
import numble.carrotmarket.domain.user.UserRepositroy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRespository productRespository;
    private final UserRepositroy userRepositroy;
    private final S3ApiProvider s3ApiProvider;
    private final InterestProductRepository interestProductRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        Page<Product> products = productRespository.findAll(pageable);
        return ProductResponse.pageOf(products);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductResponseById(Long id) {
        Product product = productRespository.findById(id).orElseThrow(CustomException::new);
        return ProductResponse.of(product);
    }


    @Transactional
    public Product registerProdcut(ProductRequest productRequest, String email) {
        User user = findUserByEmail(email);
        List<ProductImage> imageUrlList = productRequest.getProductImages().stream()
                .map(s -> new ProductImage(s3ApiProvider.uploadFile(s, String.format("%s/%s/%s.%s", user.getUserName(), UUID.randomUUID(), s.getOriginalFilename(), s.getContentType()))))
                .collect(Collectors.toList());
        return productRespository.save(productRequest.toEntity(imageUrlList, user));
    }

    @Transactional
    public void addInterestedProduct(Long productId, String email) {
        User user = findUserByEmail(email);
        Product product = productRespository.findById(productId).orElseThrow(CustomException::new);
        interestProductRepository.save(product.addInterestedProduct(user.getId()));
    }

    @Transactional
    public void deleteInterestedProduct(Long productId, String email) {
        User user = findUserByEmail(email);
        Product product = productRespository.findById(productId).orElseThrow(CustomException::new);
        interestProductRepository.delete(product.deleteInterestedProduct(user.getId()));
    }

    private User findUserByEmail(String email) {
        User user = userRepositroy.findByUserEmail(email).orElseThrow(CustomException::new);
        return user;
    }
}
