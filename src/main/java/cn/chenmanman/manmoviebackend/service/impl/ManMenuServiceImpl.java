package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.mapper.ManMenuMapper;
import cn.chenmanman.manmoviebackend.mapper.ManRoleMapper;
import cn.chenmanman.manmoviebackend.service.ManMenuService;
import cn.chenmanman.manmoviebackend.service.ManRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service.impl
 * @className ManMenuServiceImpl
 * @description TODO
 * @date 2023/6/3 21:29
 */
@Service
public class ManMenuServiceImpl  extends ServiceImpl<ManMenuMapper, ManMenuEntity> implements ManMenuService {
}
