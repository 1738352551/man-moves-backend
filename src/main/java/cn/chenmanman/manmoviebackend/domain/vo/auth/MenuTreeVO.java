package cn.chenmanman.manmoviebackend.domain.vo.auth;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className MenuTreeVO
 * @description TODO
 * @date 2023/6/7 1:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTreeVO extends ManMenuEntity {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuTreeVO> children;
}
