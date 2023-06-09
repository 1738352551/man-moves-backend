package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.MenuTreeVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterTreeVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className ManMenuService
 * @description TODO
 * @date 2023/6/3 21:28
 */
public interface ManMenuService extends IService<ManMenuEntity> {

    List<RouterTreeVO> getAntDesignRoutesTree();

    List<RouterVO> getAntDesignRoutesArr();

    List<MenuTreeVO> getMenuTreeList();

    LambdaQueryWrapper<ManMenuEntity> getQueryWrapper(MenuQueryRequest menuQueryRequest);

    void deleteMenu(List<Long> ids);

    MenuTreeVO getMenu(Long id);

    public List<MenuTreeVO> getMenuTreeChildren(Long id, List<ManMenuEntity> allMenu);
}
