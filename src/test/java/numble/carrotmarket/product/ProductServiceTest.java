package numble.carrotmarket.product;

import numble.carrotmarket.product.domain.*;
import numble.carrotmarket.product.dto.ProductRequest;
import numble.carrotmarket.product.dto.ProductResponse;
import numble.carrotmarket.user.User;
import numble.carrotmarket.user.UserRepositroy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProductServiceTest {

    private static final String filename1 = "carrot-market-erd";
    private static final String filename2 = "carrot-market-erd2_test";
    private static final String contentType = "png";
    private static final String filePath1 = "/test-img/carrot-market-erd.png";
    private static final String filePath2 = "/test-img/carrot-market-erd2_test.png";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private UserRepositroy userRepositroy;

    @Autowired
    private InterestProductRepository interestProductRepository;

    private User user;
    private Product product;

    @BeforeEach
    void setup() {
        user = userRepositroy.save(new User("choieungi@gmail.com", "eungi", "123", "010-1234-4567", "goose"));
        product = productRespository.save(new Product("title", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user));
    }

    @Test
    void findThreeProductsTest() {
        productRespository.save(new Product("title1", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user));
        productRespository.save(new Product("title2", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user));
        productRespository.save(new Product("title3", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user));
        productRespository.save(new Product("title4", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user));
        productRespository.save(new Product("title5", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user));

        Pageable pageable = PageRequest.of(0, 3);
        Page<ProductResponse> allProducts = productService.findAllProducts(pageable);
        for (ProductResponse allProduct : allProducts) {
            System.out.println("allProduct = " + allProduct.getTitle());
            System.out.println("allProduct = " + allProduct.getCreatedAt());
        }
    }

    @Test
    void registerProduct() throws IOException {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(getMockMultipartFile(filename1, filePath1));
        imageFiles.add(getMockMultipartFile(filename2, filePath2));
        Product product = productService.registerProdcut(new ProductRequest("title", 10000, "selling", ProductState.SOLDING, Category.CLOTHES, imageFiles), user.getUserEmail());

    }

    private MultipartFile getMockMultipartFile(String filename, String filePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        FileInputStream fileInputStream = new FileInputStream(classPathResource.getFile());
        return new MockMultipartFile(filename, filename + "." + contentType, contentType, fileInputStream);
    }

    @Test
    void addInterestedPostTest() {
        productService.addInterestedProduct(product.getId(), user.getUserEmail());
        Product appliedProduct = productRespository.findById(product.getId()).orElseThrow(IllegalArgumentException::new);
        assertThat(appliedProduct.getInterestedProductCount()).isEqualTo(1);
    }

    @Test
    void deleteInterestedPostTest() {
        productService.addInterestedProduct(product.getId(), user.getUserEmail());
        interestProductRepository.findInterestProductsByProductId(product.getId()).orElseThrow(IllegalArgumentException::new);
        Product addedProduct = productRespository.findById(product.getId()).orElseThrow(IllegalArgumentException::new);
        productService.deleteInterestedProduct(addedProduct.getId(), user.getUserEmail());
        Product deletedProduct = productRespository.findById(product.getId()).orElseThrow(IllegalArgumentException::new);
        assertThat(deletedProduct.getInterestedProductCount()).isEqualTo(0);
    }


    @AfterEach
    void tearDown() {
        interestProductRepository.deleteAllInBatch();
        productRespository.deleteAllInBatch();
        userRepositroy.deleteAllInBatch();
    }

}