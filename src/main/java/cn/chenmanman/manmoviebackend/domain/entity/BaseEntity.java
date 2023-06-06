package cn.chenmanman.manmoviebackend.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.entity
 * @className BaseEntity
 * @author 陈慢慢
 * @description 基础实体
 * @date 2023/5/1 17:32
 * @version 1.0
 */
@Data
public class BaseEntity {
    /**
     * 主键id
     * */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     * */
    private LocalDateTime createTime;

    /**
     * 创建人
     * */
    private Long createBy;
    /**
     * 修改时间
     * */
    private LocalDateTime updateTime;
    /**
     * 修改人
     * */
    private Long updateBy;
    /**
     * 逻辑删除
     * */
    @TableLogic
    private Integer isDelete;
}
