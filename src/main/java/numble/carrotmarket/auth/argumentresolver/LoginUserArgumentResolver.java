package numble.carrotmarket.auth.argumentresolver;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import numble.carrotmarket.auth.JWTProvider;
import numble.carrotmarket.exception.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static numble.carrotmarket.auth.AuthConst.ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JWTProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class) && parameter.getParameterType().isAssignableFrom(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            throw new AuthenticationException("요청이 존재하지 않습니다.");
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(ACCESS_TOKEN)) {
                DecodedJWT decodedJWT = jwtProvider.verifyAndDecodeJwt(cookie.getValue());
                return decodedJWT.getSubject();
            }
        }

        throw new AuthenticationException("토큰이 유효하지 않습니다.");

    }
}

