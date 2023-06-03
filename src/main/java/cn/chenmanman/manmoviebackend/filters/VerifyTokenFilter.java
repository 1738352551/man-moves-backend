package cn.chenmanman.manmoviebackend.filters;



import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.common.utils.RedisUtil;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserEntity;
import cn.chenmanman.manmoviebackend.common.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * token验证过滤器
 *
 * */
@Slf4j
@Component
public class VerifyTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (Objects.isNull(token) || token.equals("")) {
            filterChain.doFilter(request, response);
            return;
        }
        String loginUser = "loginUser:";
        try {
            Claims claims = TokenUtil.parseJWT(token);
            loginUser += claims.getSubject();
        } catch (Exception e) {
            throw new BusinessException("token非法!", 500L);
        }
        ManUserEntity manUserEntity = (ManUserEntity) redisUtil.get(loginUser);;
        Optional.ofNullable(manUserEntity).orElseThrow(()->new BusinessException("未登录!", 500L));

        UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(manUserEntity, null, manUserEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token1);
        filterChain.doFilter(request, response);
    }
}
