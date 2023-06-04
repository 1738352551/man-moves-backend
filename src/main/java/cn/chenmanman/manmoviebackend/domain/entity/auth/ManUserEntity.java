package cn.chenmanman.manmoviebackend.domain.entity.auth;

import cn.chenmanman.manmoviebackend.domain.entity.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.entity.auth
 * @className User
 * @description 用户实体类,这个类要去继承UserDetails，这个UserDetails也是springsecurity的
 * @date 2023/6/3 20:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"authorities"})
@TableName("man_user")
public class ManUserEntity extends BaseEntity implements UserDetails {
    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

    /**
     * 邮箱
     * */
    private String email;


    /**
     * 头像
     * */
    private String avatar;
    /**
     *
     * 昵称
     * */
    private String nickname;

    /**
     * 性别
     * */
    private Integer gender;

    /**
     * 用户状态
     * */
    private Integer status;

    /**
     * 登录状态
     * */
    private Integer loginStatus;


    /**
     * 上次登录ip
     * */
    private String lastLoginIp;

    /**
     * 上次登录时间
     * */
    private Date lastLoginDate;

    @TableField(exist = false)
    @JsonIgnore
    @JSONField(serialize = false)
    private Collection<SimpleGrantedAuthority> authorities;

    @TableField(exist = false)
    private List<String> permissions;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }
        return this.permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status != 0;
    }
}
