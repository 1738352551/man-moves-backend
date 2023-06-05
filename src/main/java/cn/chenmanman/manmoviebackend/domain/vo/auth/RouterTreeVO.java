package cn.chenmanman.manmoviebackend.domain.vo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className RouterVO
 * @description  路由表生成
 * @date 2023/6/5 1:31
 */
@Data
public class RouterTreeVO {

    @JsonIgnore
    private Long id;

    /**
     * 菜单标题
     * */
    private String title;

    /**
     * 菜单标识
     * */
    private String key;

    /**
     * 组件地址
     * */
    private String component;
    /**
     * 重定向地址
     * */
    private String redirect;


    private List<RouterTreeVO> children;
}
