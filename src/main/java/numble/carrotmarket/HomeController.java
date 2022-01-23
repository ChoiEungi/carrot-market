package numble.carrotmarket;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(){
        return "index";
    }

//    @GetMapping("/favicon.ico")
//    public void favicon(HttpServletResponse response) throws IOException {
//        try {
//            response.sendRedirect("/static/favicon.ico");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
