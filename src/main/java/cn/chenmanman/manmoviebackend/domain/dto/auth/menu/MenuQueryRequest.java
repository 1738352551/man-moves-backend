package cn.chenmanman.manmoviebackend.domain.dto.auth.menu;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth.menu
 * @className MenuQueryRequest
 * @description 菜单查询请求
 * @date 2023/6/7 11:11
 */
@Data
public class MenuQueryRequest {
    private String title;
    private Integer MenuStatus;
}
