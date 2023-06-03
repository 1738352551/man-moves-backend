package cn.chenmanman.manmoviebackend.domain.entity.auth;

import cn.chenmanman.manmoviebackend.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @TableName man_role_menu
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="man_role_menu")
@Data
public class ManRoleMenuEntity extends BaseEntity implements Serializable {
    /**
     * 主键Id
     */
    @TableId
    private Long id;

    /**
     *
     */
    private Long menuId;

    /**
     *
     */
    private Long roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
