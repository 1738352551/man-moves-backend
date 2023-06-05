package cn.chenmanman.manmoviebackend.domain.vo.auth;

import lombok.Data;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className MetaVO
 * @description TODO
 * @date 2023/6/5 13:11
 */

@Data
public class MetaVO {
    private String title;

    private String icon;
    //缓存该路由 (开启 multi-tab 是默认值为 true)
    private boolean keepAlive;
    //hiddenHeaderContent
    private boolean hiddenHeaderContent;

    private List<String> permission;
}
