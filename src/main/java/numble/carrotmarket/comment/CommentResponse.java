package numble.carrotmarket.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.carrotmarket.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentResponse {

    private String userName;
    private String userImage;
    private String description;
    private String createdAt;

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getUser().getUserName(),
                comment.getUser().getUserImageUrl(),
                comment.getDescription(),
                comment.getformattedDate(LocalDateTime.now())
        );
    }

    public static List<CommentResponse> listOf(List<Comment> comments){
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

}
