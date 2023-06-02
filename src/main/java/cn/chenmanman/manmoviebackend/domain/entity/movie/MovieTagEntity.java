package cn.chenmanman.manmoviebackend.domain.entity.movie;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.entity
 * @className MovieTagEntity
 * @description 影视与标签的中间表
 * @date 2023/5/31 0:06
 */
@Data
@TableName("movie_tag")
public class MovieTagEntity {

    /**
     * 标签id
     * */
    private Long tagId;

    /**
     * 影视id
     * */
    private Long movieId;

    /**
     * 备注
     * */
    private String remark;
}
