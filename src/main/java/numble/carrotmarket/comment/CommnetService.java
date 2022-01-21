package numble.carrotmarket.comment;


import lombok.RequiredArgsConstructor;
import numble.carrotmarket.exception.CustomException;
import numble.carrotmarket.product.domain.Product;
import numble.carrotmarket.product.domain.ProductRespository;
import numble.carrotmarket.user.User;
import numble.carrotmarket.user.UserRepositroy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommnetService {

    private final CommentRepository commentRepository;
    private final ProductRespository productRespository;
    private final UserRepositroy userRepositroy;

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllCommentById(Long productId) {
        Product product = productRespository.findById(productId).orElseThrow(CustomException::new);
        List<Comment> comments = commentRepository.findCommentsByProductId(product.getId()).orElseThrow(CustomException::new);
        return CommentResponse.listOf(comments);
    }

    @Transactional(readOnly = true)
    public CommentResponse findCommentResponeById(Long commnetId) {
        Comment comment = commentRepository.findById(commnetId).orElseThrow(CustomException::new);
        return CommentResponse.of(comment);
    }

    @Transactional
    public Long createComment(CommentRequest commentRequest, Long productId, String email) {
        User user = findUserByEmail(email);
        Product product = productRespository.findById(productId).orElseThrow(CustomException::new);
        return commentRepository.save(new Comment(commentRequest.getDescription(), product, user)).getId();
    }

    @Transactional
    public void updateComment(CommentRequest commentRequest, Long commentId, String email) {
        Comment comment = checkCommentWriter(email, commentId);
        comment.updateComment(commentRequest.getDescription());
    }

    @Transactional
    public void deleteComment(Long commentId, String email) {
        Comment comment = checkCommentWriter(email, commentId);
        commentRepository.delete(comment);
    }

    private Comment checkCommentWriter(String email, Long commentId) {
        User user = findUserByEmail(email);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CustomException::new);
        if (!comment.getUser().equals(user)) {
            throw new CustomException("댓글 작성자가 아닙니다.");
        }
        return comment;
    }

    private User findUserByEmail(String email) {
        return userRepositroy.findByUserEmail(email).orElseThrow(CustomException::new);
    }
}
