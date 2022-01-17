package numble.carrotmarket.user;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class User {

    public static final String BASIC_USER_IMAGE_DIR = "user_images/";
    private static final String BASIC_IMAGE_URL_KEY = BASIC_USER_IMAGE_DIR + "basic_user_image.png";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    private String userPassword;

    private String userPhoneNumber;

    private String userNickname;

    private String userImageUrl;

    protected User() {
    }

    public User(String userName, String userPassword, String userPhoneNumber, String userNickname) {
        this(null, userName, userPassword, userPhoneNumber, userNickname);
    }

    private User(Long userId, String userName, String userPassword, String userPhoneNumber, String userNickname) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userNickname = userNickname;
        this.userImageUrl = BASIC_IMAGE_URL_KEY;
    }

    public void changeToBasicImage(){
        this.userImageUrl = BASIC_IMAGE_URL_KEY;
    }

    public void changeBaseImageToOtherImage(String otherImageUrl){
        this.userImageUrl = otherImageUrl;
    }


}
