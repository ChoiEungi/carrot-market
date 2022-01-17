package numble.carrotmarket.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

class JWTProviderTest {

    private static final String SUBJECT = "choieungi@gm.gist.ac.kr";
    private static final JWTProvider JWT_PROVIDER = new JWTProvider();
    private static final String invalidSecretKey = "invalidSecretKey";
    private static final long DURATION_TIME = 10 * 60 * 1000;

    private String accessToken;

    @BeforeEach
    void setup() {
        accessToken = JWT_PROVIDER.createAccessToken(SUBJECT);
    }

    @Test
    void createAccessTokenTest() {
        assertThat(JWT.decode(accessToken).getSubject()).isEqualTo(SUBJECT);
    }

    @Test
    void verifyandDecodeTheJWT() {
        DecodedJWT decodedJWT = JWT_PROVIDER.verifyAndDecodeJwt(accessToken);
        String[] jwtArray = accessToken.split("\\.");
        String header = jwtArray[0];
        String payload = jwtArray[1];
        String signature = jwtArray[2];

        assertThat(decodedJWT.getHeader()).isEqualTo(header);
        assertThat(decodedJWT.getPayload()).isEqualTo(payload);
        assertThat(decodedJWT.getSignature()).isEqualTo(signature);
    }


    @Test
    @DisplayName("secretKey가 다른 알고리즘을 검증할 때 Exception을 출력하는 테스트")
    void signitureVerifyFailByInvalidSecretKeyTest() {
        Algorithm invalidAlgorithm = Algorithm.HMAC256(invalidSecretKey);
        String invalidJWT = createJWT(SUBJECT, invalidAlgorithm,DURATION_TIME);

        assertThatThrownBy(() -> JWT_PROVIDER.verifyAndDecodeJwt(invalidJWT))
                .isInstanceOf(JWTVerificationException.class);
    }

    @Test
    @DisplayName("종류가 다른 알고리즘을 검증할 때 Exception을 출력하는 테스트")
    void signitureVerifyFailByDifferentAlgorithm() {
        Algorithm HMAC384 = Algorithm.HMAC384(invalidSecretKey);
        Algorithm HMAC256 = Algorithm.HMAC256(invalidSecretKey);
        JWTVerifier jwtVerifier = JWT.require(HMAC256).build();
        String jwt = createJWT(SUBJECT, HMAC384,DURATION_TIME);

        assertThatThrownBy(() -> jwtVerifier.verify(jwt))
                .isInstanceOf(JWTVerificationException.class);
    }

    @Test
    void signitureVerifyFailByExpiration() {
        Algorithm algorithm = Algorithm.HMAC256(invalidSecretKey);
        String invalidJWT = createJWT(SUBJECT, algorithm, -DURATION_TIME);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        assertThatThrownBy(() -> jwtVerifier.verify(invalidJWT))
                .isInstanceOf(JWTVerificationException.class);
    }

    private String createJWT(String subject, Algorithm algorithm, long accessTokenDurationTime) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessTokenDurationTime);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expirationTime)
                .sign(algorithm);
    }


}