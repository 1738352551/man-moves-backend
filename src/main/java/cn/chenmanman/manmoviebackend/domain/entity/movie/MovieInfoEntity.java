package cn.chenmanman.manmoviebackend.domain.entity.movie;

import cn.chenmanman.manmoviebackend.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 影视信息
 * @TableName movie_info
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="movie_info")
@Data
public class MovieInfoEntity extends BaseEntity implements Serializable {


    /**
     * 影视名
     */
    private String name;

    /**
     * 影视介绍
     */
    private String introduction;

    /**
     * 影片封面
     */
    private String bannerUrl;

    /**
     * 影片评分
     */
    private Integer score;

    /**
     * 观看人数
     */
    private Long viewNum;

    /**
     * 影视类型
     */
    private Integer type;

    /**
     * 腾讯视频cid
     * */
    private String cid;

    /**
     * 视频来源
     * */
    private String videoSource;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
