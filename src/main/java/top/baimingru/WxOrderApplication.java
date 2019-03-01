package top.baimingru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WxOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxOrderApplication.class, args);
    }

}

