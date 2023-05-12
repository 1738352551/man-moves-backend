package cn.chenmanman.manmoviebackend.domain.dto.movie.tencent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie.tencent
 * @className TencentMoviePullPostRequest
 * @description TODO
 * @date 2023/5/12 14:57
 */
@Data
@ApiModel("腾讯视频抓取Post请求参数")
public class TencentMoviePullPostRequest {
    /**
     * 影视类型: 1001: 电影  1002:电视剧  1003:动漫
     * */
    @ApiModelProperty("影视类型")
    private String type;
}
