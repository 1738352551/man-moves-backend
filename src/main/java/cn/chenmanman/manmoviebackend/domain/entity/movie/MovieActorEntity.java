package cn.chenmanman.manmoviebackend.domain.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 影视演职员表
 * @TableName movie_actor
 */
@TableName(value ="movie_actor")
@Data
@ApiModel(value = "影视演员")
public class MovieActorEntity implements Serializable {
    /**
     * 影视id
     */

    private Long movieId;

    /**
     * 演员id
     */
    private Long actorId;

    /**
     * 职位
     */
    private Integer posts;

    /**
     * 扮演人名
     */
    private String cosplayName;

    /**
     *
     */
    private String cosplayPhoto;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
