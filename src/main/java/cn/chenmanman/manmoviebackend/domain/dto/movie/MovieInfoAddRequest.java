package cn.chenmanman.manmoviebackend.domain.dto.movie;

import cn.chenmanman.manmoviebackend.domain.entity.MovieActorEntity;
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
 * @className MovieInfoAddRequest
 * @description 添加影视信息请求
 * @date 2023/5/30 0:32
 */
@Data
@ApiModel(value = "添加影视信息请求")
public class MovieInfoAddRequest {
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


//    private Long actorId;
//    private String cosplayName;
//    private Integer posts;
//    private String cosplayPhoto;

    /**
     * 演员表
     * */
    @ApiModelProperty(value = "演员表", required = false)
    private List<MovieActorEntity> movieActor;

    /**
     * 影视标签
     * */
    @ApiModelProperty(value = "影视标签", required = false)
    private List<Long> tagId;
}
