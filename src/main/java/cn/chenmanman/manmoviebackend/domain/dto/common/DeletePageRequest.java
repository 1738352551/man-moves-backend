package cn.chenmanman.manmoviebackend.domain.dto.common;

import lombok.Data;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.common
 * @className DeletePageRequest
 * @description TODO
 * @date 2023/5/30 0:29
 */
@Data
public class DeletePageRequest {
    /**
     * 主键Id
     * */
    private List<Long> ids;
}
