package numble.carrotmarket.domain.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static numble.carrotmarket.domain.auth.AuthConst.ACCESS_TOKEN;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || Arrays.stream(cookies).noneMatch(s -> s.getName().equals(ACCESS_TOKEN))) {
            response.sendRedirect("/");
            return false;
//            throw new AuthenticationException("토큰이 존재하지 않습니다.");
        }
        return true;
    }
}
