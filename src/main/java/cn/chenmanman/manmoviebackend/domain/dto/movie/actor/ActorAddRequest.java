package cn.chenmanman.manmoviebackend.domain.dto.movie.actor;

import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie.actor
 * @className ActorAddRequest
 * @description  演员信息添加请求
 * @date 2023/6/3 1:30
 */
@Data
public class ActorAddRequest {
    private String name;


    private String photoUrl;
}
