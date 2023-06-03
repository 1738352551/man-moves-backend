package cn.chenmanman.manmoviebackend.config;

import cn.chenmanman.manmoviebackend.common.exception.AuthenticationSuccessHandlerImpl;
import cn.chenmanman.manmoviebackend.filters.VerifyTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.config
 * @className SecurityConfiguration
 * @description SpringSecurity配置类
 * @date 2023/6/3 20:17
 */


// 这里注解的部分，还没加权限那个注解
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(type = VerifyTokenFilter.class)
    private VerifyTokenFilter verifyTokenFilter;

    @Resource(type = AccessDeniedHandler.class)
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    /**
     *
     * 前后分离的要在这里配置security
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf：需要禁用跨站访问的配置
        // cors：允许跨域
        http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 这里是对一些接口进行设置需不需要认证， .antMatchers("/**").anonymous()说明可以匿名访问呗匹配的接口
                // .anyRequest().authenticated(); 表示剩下的接口需要认证之后才能使用
                .authorizeRequests().antMatchers("/user/login").anonymous().anyRequest().authenticated();


        // 把验证token的过滤器放到UsernamePasswordAuthenticationFilter前面
        http.addFilterBefore(verifyTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationSuccessHandler);
    }

    /**
     * 这个是SpringSecurity的不分离部分配置的，这是对swagger让security忽略他们不然swagger就进不去了。
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v2/**")
                .antMatchers("/v2/api-docs-ext/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/doc.html");
    }

    /**
     * 这个是SpringSecurity的认证管理器, 这个是我们做认证的时候会用到的所以给他注册成一个bean
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 这个方法是SpringSecurity的密码编码器, 这个是我们做密码加密的时候会用到的所以也给他注册成一个Bean,
     * PasswordEncoder有很多实现，这个编码方式的实现是最常用的.
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
