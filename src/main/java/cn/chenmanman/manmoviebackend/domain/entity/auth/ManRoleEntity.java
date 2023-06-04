package cn.chenmanman.manmoviebackend.domain.entity.auth;

import cn.chenmanman.manmoviebackend.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.entity.auth
 * @className ManRole
 * @description 角色实体类
 * @date 2023/6/3 20:36
 */
@Data
@TableName("man_role")
@EqualsAndHashCode(callSuper = true)
public class ManRoleEntity extends BaseEntity {

        /**
         * 角色名
         * */
        private String name;

        /**
         * 角色标识
         * */
        private String roleKey;

        /**
         *  角色状态(0:禁止, 1: 正常)
         * */
        private Integer status;
}
