package cn.chenmanman.manmoviebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 标签表
 * @TableName tag
 */
@TableName(value ="tag")
@Data
public class TagEntity implements Serializable {
    /**
     * 主键Id
     */
    @TableId
    private Integer id;

    /**
     * 标签名
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}