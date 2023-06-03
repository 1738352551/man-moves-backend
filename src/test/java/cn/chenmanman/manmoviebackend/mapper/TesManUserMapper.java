package cn.chenmanman.manmoviebackend.mapper;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.mapper
 * @className TesManUserMapper
 * @description TODO
 * @date 2023/6/3 22:33
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TesManUserMapper {
    @Resource
    private ManUserMapper manUserMapper;

    @Test
    public void testGetAuths() {
        List<String> userAuthoritiesByUserId = manUserMapper.getUserAuthoritiesByUserId(1L);
        log.info(userAuthoritiesByUserId.toString());
    }


    @Resource
    private PasswordEncoder passwordEncoder;
    @Test
    public void generateAPassword() {
        log.info(passwordEncoder.encode("123123"));
    }
}
