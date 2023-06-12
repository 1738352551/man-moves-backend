package cn.chenmanman.manmoviebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName files
 */
@TableName(value ="files")
@Data
public class Files extends BaseEntity implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String fileName;

    private String fileUrl;

    /**
     *
     */
    private String fileType;

    /**
     *
     */
    private Date uploadTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
