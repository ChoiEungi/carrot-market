package numble.carrotmarket.product;

import numble.carrotmarket.product.domain.Category;
import numble.carrotmarket.product.domain.Product;
import numble.carrotmarket.product.domain.ProductRespository;
import numble.carrotmarket.product.domain.ProductState;
import numble.carrotmarket.product.dto.ProductRequest;
import numble.carrotmarket.user.User;
import numble.carrotmarket.user.UserRepositroy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private User user;

    @BeforeEach
    void setup() {
        user = userRepositroy.save(new User("choieungi@gmail.com", "eungi", "123", "010-1234-4567", "goose"));
    }

    @Test
    void registerProdcut() throws IOException {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(getMockMultipartFile(filename1, filePath1));
        imageFiles.add(getMockMultipartFile(filename2, filePath2));
        Product product= productService.registerProdcut(new ProductRequest("title", 10000, "selling", ProductState.SOLDING, Category.CLOTHES, imageFiles), user.getUserEmail());

    }

    private MultipartFile getMockMultipartFile(String filename, String filePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        FileInputStream fileInputStream = new FileInputStream(classPathResource.getFile());
        return new MockMultipartFile(filename, filename + "." + contentType, contentType, fileInputStream);
    }

    @AfterEach
    void tearDown() {
        productRespository.deleteAllInBatch();
        userRepositroy.deleteAllInBatch();
    }

}