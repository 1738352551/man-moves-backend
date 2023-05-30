package cn.chenmanman.manmoviebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @TableName video_api
 */
@TableName(value ="video_api")
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoApiEntity extends BaseEntity implements Serializable {
    /**
     * 主键Id
     */
    @TableId
    private Long id;

    /**
     * 视频解析接口名字
     */
    private String name;

    /**
     * 视频解析接口地址
     */
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
