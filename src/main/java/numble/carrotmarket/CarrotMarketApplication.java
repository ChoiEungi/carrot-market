package numble.carrotmarket;

import numble.carrotmarket.user.application.UserService;
import numble.carrotmarket.user.dto.SignUpRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarrotMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotMarketApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            Long userId1 = userService.createUser(new SignUpRequest("choieungi@gm.gist.ac.kr", "eungi", "123", "010-1234-5678","goose"));
        };
    }

}
