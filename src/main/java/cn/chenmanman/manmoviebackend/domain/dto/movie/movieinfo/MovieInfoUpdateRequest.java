package cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo;

import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieActorEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie
 * @className MovieInfoUpdateRequest
 * @description 影视/电视剧修改请求
 * @date 2023/5/30 0:32
 */
@Data
@ApiModel(description = "影视/电视修改请求")
public class MovieInfoUpdateRequest {

    /**
     * 影视id
     * */
    @ApiModelProperty(value = "影视id", required = true)
    private Long id;

    /**
     * 影视名
     */
    @ApiModelProperty(value = "影视名", required = true)
    @NotBlank(message = "请填写影视名!")
    private String name;

    /**
     * 影视介绍
     */
    @ApiModelProperty(value = "影视介绍", required = false)
    private String introduction;

    /**
     * 影片封面
     */
    @ApiModelProperty(value = "影片封面", required = false)
    private String bannerUrl;


    /**
     * 影视类型
     */
    @ApiModelProperty(value = "影视类型", required = false)
    private Integer type;

    @ApiModelProperty(value = "影视关联的演员", required = false)
    private List<MovieActorEntity> movieActor;

    @ApiModelProperty(value = "影视关联的标签", required = false)
    private List<Long> tagId;
}
