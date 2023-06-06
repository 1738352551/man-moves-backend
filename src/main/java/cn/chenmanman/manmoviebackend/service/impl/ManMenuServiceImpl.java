package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.MenuTreeVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.MetaVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterTreeVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterVO;
import cn.chenmanman.manmoviebackend.mapper.ManMenuMapper;
import cn.chenmanman.manmoviebackend.service.ManMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service.impl
 * @className ManMenuServiceImpl
 * @description 菜单服务
 * @date 2023/6/3 21:29
 */
@Service
public class ManMenuServiceImpl  extends ServiceImpl<ManMenuMapper, ManMenuEntity> implements ManMenuService {



    @Override
    public List<RouterTreeVO> getAntDesignRoutesTree() {
        // 所有菜单
        List<ManMenuEntity> manMenuEntities = this.baseMapper.selectList(null);

        // 所有父级菜单
        List<RouterTreeVO> rootMenus = new ArrayList<>();
        for (ManMenuEntity manMenuEntity : manMenuEntities) {
            if (manMenuEntity.getParentId().equals(0L)) {
                RouterTreeVO routerTreeVO = new RouterTreeVO();
                BeanUtils.copyProperties(manMenuEntity, routerTreeVO);
                routerTreeVO.setKey(manMenuEntity.getMenuKey());
                routerTreeVO.setComponent(manMenuEntity.getComponent());
                routerTreeVO.setRedirect(manMenuEntity.getRedirect());
                rootMenus.add(routerTreeVO);
            }
        }

        // 寻找父级菜单的子菜单
        for (RouterTreeVO rootMenu : rootMenus) {
            rootMenu.setChildren(getChild(rootMenu.getId(), manMenuEntities));
        }

        return rootMenus;
    }

    @Override
    public List<RouterVO> getAntDesignRoutesArr() {
        List<ManMenuEntity> manMenuEntities = this.baseMapper.selectList(null);
        return manMenuEntities.stream().map(manMenu -> {
            RouterVO routerVO = new RouterVO();
            routerVO.setName(manMenu.getMenuKey());
            routerVO.setComponent(manMenu.getComponent());
            routerVO.setKey(manMenu.getMenuKey());
            routerVO.setPath(manMenu.getPath());
            routerVO.setRedirect(manMenu.getRedirect());
            routerVO.setId(manMenu.getId());
            routerVO.setParentId(manMenu.getParentId());
            MetaVO metaVO = new MetaVO();
            metaVO.setTitle(manMenu.getTitle());
            metaVO.setIcon(manMenu.getIcon());
//            metaVO.setPermission(manMenu.getPermission());
            routerVO.setMeta(metaVO);
            return routerVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MenuTreeVO> getMenuTreeList() {
        // 所有菜单
        List<ManMenuEntity> manMenuEntities = this.baseMapper.selectList(null);

        // 所有父级菜单
        List<MenuTreeVO> rootMenus = new ArrayList<>();
        for (ManMenuEntity manMenuEntity : manMenuEntities) {
            if (manMenuEntity.getParentId().equals(0L)) {
                MenuTreeVO menuTreeVO = new MenuTreeVO();
                BeanUtils.copyProperties(manMenuEntity, menuTreeVO);
                rootMenus.add(menuTreeVO);
            }
        }

        // 寻找父级菜单的子菜单
        for (MenuTreeVO rootMenu : rootMenus) {
            rootMenu.setChildren(getMenuTreeChildren(rootMenu.getId(), manMenuEntities));
        }

        return rootMenus;
    }

    private List<MenuTreeVO> getMenuTreeChildren(Long id, List<ManMenuEntity> allMenu) {
        List<MenuTreeVO> childList = new ArrayList<>();
        for (ManMenuEntity entity : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (entity.getParentId().equals(id)) {
                MenuTreeVO menuTreeVO = new MenuTreeVO();
                BeanUtils.copyProperties(entity, menuTreeVO);
                childList.add(menuTreeVO);
            }
        }
        //递归
        for (MenuTreeVO entity : childList) {
            entity.setChildren(getMenuTreeChildren(entity.getId(), allMenu));
        }
        //Collections.sort(childList,order()); //排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
    private List<RouterTreeVO> getChild(Long id, List<ManMenuEntity> allMenu) {
//子菜单
        List<RouterTreeVO> childList = new ArrayList<>();
        for (ManMenuEntity entity : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (entity.getParentId().equals(id)) {
                RouterTreeVO routerTreeVO = new RouterTreeVO();
                BeanUtils.copyProperties(entity, routerTreeVO);
                routerTreeVO.setKey(entity.getMenuKey());
                routerTreeVO.setComponent(entity.getComponent());
                routerTreeVO.setRedirect(entity.getRedirect());
                childList.add(routerTreeVO);
            }
        }
        //递归
        for (RouterTreeVO entity : childList) {
            entity.setChildren(getChild(entity.getId(), allMenu));
        }
        //Collections.sort(childList,order()); //排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}
