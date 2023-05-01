package cn.chenmanman.manmoviebackend;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = ManMovieBackendApplication.class)
@Slf4j
class ManMovieBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testLog() {
        log.trace("hello, I am trace!");
        log.debug("hello, I am debug!");
        log.info("hello, I am info!");
        log.warn("hello, I am {}!", "warn");
        log.error("hello, I am {}!", "error");

    }

}
