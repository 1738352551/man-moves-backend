package cn.chenmanman.manmoviebackend.domain.entity.movie;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName actor
 */
@TableName(value ="actor")
@Data
public class ActorEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 演员名
     */
    private String name;

    /**
     * 演员照片
     */
    private String photoUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
