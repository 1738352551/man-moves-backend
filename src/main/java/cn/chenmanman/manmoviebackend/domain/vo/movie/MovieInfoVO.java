package cn.chenmanman.manmoviebackend.domain.vo.movie;

import cn.chenmanman.manmoviebackend.domain.entity.TagEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.movie
 * @className MovieInfoVO
 * @description 影视信息VO
 * @date 2023/6/2 16:32
 */
@Data
public class MovieInfoVO {
    /**
     * 影视id
     * */
    private Long movieId;
    /**
     * 影视名
     * */
    private String movieName;
    /**
     * 影视类型
     * */
    private Integer movieType;
    /**
     * 影视宣传图
     * */
    private String bannerUrl;
    /**
     * 影视观看数
     * */
    private Long viewNum;
    /**
     * 影视分数
     * */
    private Integer score;
    /**
     * 影视简介
     * */
    private String introduction;
    /**
     * 影视演员表
     * */
    private List<Map<String, Object>> actorList;

    /**
     * 影视标签
     * */
    private List<TagEntity> tagList;
}
