package cn.chenmanman.manmoviebackend.domain.entity.movie;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName episodes
 */

@TableName(value ="episodes")
@Data
public class EpisodesEntity implements Serializable {
    /**
     * 主键Id
     */
    @TableId
    private Long id;

    /**
     * 影视id
     */
    private Long movieId;

    /**
     * 视频地址
     */
    private String movieUrl;

    /**
     * 分集视频标题
     * */
    private String title;

    /**
     * 是否为最新
     */
    private Integer isNew;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
