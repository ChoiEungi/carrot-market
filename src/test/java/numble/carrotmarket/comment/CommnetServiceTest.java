package numble.carrotmarket.comment;

import numble.carrotmarket.domain.comment.*;
import numble.carrotmarket.domain.exception.CustomException;
import numble.carrotmarket.domain.product.domain.Category;
import numble.carrotmarket.domain.product.domain.Product;
import numble.carrotmarket.domain.product.domain.ProductRespository;
import numble.carrotmarket.domain.product.domain.ProductState;
import numble.carrotmarket.domain.user.User;
import numble.carrotmarket.domain.user.UserRepositroy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class CommnetServiceTest {
    public static final CommentRequest COMMENT_REQUEST = new CommentRequest("description");
    @Autowired
    private UserRepositroy userRepositroy;
    @Autowired
    private ProductRespository productRespository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommnetService commnetService;

    private User user1;
    private User user2;
    private Product product;
    private Comment comment;

    @BeforeEach
    void setup() {
        user1 = userRepositroy.save(new User("eungi6850@gmail.com", "eungi", "123", "010-1234-5678", "goose"));
        user2 = userRepositroy.save(new User("eungi6850@naver.com", "eungi", "123", "010-1234-5678", "goose"));
        product = productRespository.save(new Product("title", 10000, "content", ProductState.SOLDING, Category.CLOTHES, new ArrayList<>(), user1));
        comment = commentRepository.save(new Comment("description", product, user1));
    }

    @Test
    void findAllCommentById() {
        for (int i = 0; i < 5; i++) {
            commentRepository.save(new Comment("description", product, user1));
        }
        assertThat(commnetService.findAllCommentById(product.getId()).size()).isEqualTo(6);
    }

    @Test
    void findCommentResponeById() {
        CommentResponse commentResponse = commnetService.findCommentResponeById(comment.getId());
        assertThat(commentResponse.getUserName()).isEqualTo(user1.getUserName());
        assertThat(commentResponse.getUserImage()).isEqualTo(user1.getUserImageUrl());
        assertThat(commentResponse.getDescription()).isEqualTo(comment.getDescription());
        assertThat(commentResponse.getCreatedAt()).isEqualTo(comment.getformattedDate(LocalDateTime.now()));
    }

    @Test
    void createComment() {
        Long commentId = commnetService.createComment(COMMENT_REQUEST, product.getId(), user1.getUserEmail());
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        assertThat(comment.getUser().getUserEmail()).isEqualTo(user1.getUserEmail());
        assertThat(comment.getProduct().getId()).isEqualTo(product.getId());
        assertThat(comment.getDescription()).isEqualTo(COMMENT_REQUEST.getDescription());
    }

    @Test
    void updateComment() {
        CommentRequest updateCommentRequest = new CommentRequest("update_comment");
        commnetService.updateComment(updateCommentRequest, comment.getId(), user1.getUserEmail());
        Comment updatedComment = commentRepository.findById(comment.getId()).orElseThrow(CustomException::new);
        assertThat(updatedComment.getDescription()).isEqualTo(updateCommentRequest.getDescription());
    }

    @Test
    @DisplayName("user1이 작성한 댓글을 user2가 수정하는 테스트")
    void updateCommentByNotOwner() {
        CommentRequest updateCommentRequest = new CommentRequest("update_comment");
        assertThatThrownBy(
                () -> commnetService.updateComment(updateCommentRequest, comment.getId(), user2.getUserEmail())
        ).isInstanceOf(CustomException.class);
    }

    @Test
    void deleteComment() {
        Comment comment = commentRepository.save(new Comment("description", product, user1));
        commnetService.deleteComment(comment.getId(), user1.getUserEmail());
        assertThat(commentRepository.findById(comment.getId())).isEmpty();
    }

    @Test
    @DisplayName("user1이 작성한 댓글을 user2가 삭제하는 테스트")
    void deleteCommentByNotOwner() {
        Comment comment = commentRepository.save(new Comment("description", product, user1));
        assertThatThrownBy(
                () ->commnetService.deleteComment(comment.getId(), user2.getUserEmail())
        ).isInstanceOf(CustomException.class);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        productRespository.deleteAllInBatch();
        userRepositroy.deleteAllInBatch();

    }
}