package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.TagEntity;
import cn.chenmanman.manmoviebackend.domain.vo.PageResult;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RoleTableVO;
import cn.chenmanman.manmoviebackend.service.TagService;
import cn.chenmanman.manmoviebackend.service.impl.TagServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className TagController
 * @description 标签接口
 * @date 2023/6/2 23:48
 */
@RequestMapping("/tag")
@RestController
@Api(tags = "标签接口")
public class TagController {

    @Resource(type = TagServiceImpl.class)
    private TagService tagService;

    @ApiOperation(value = "添加标签")
    @PostMapping("/add")
    public CommonResult<?> addTag(@Validated @RequestBody TagAddRequest tagAddRequest) {
        tagService.addTag(tagAddRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改标签信息")
    @PostMapping("/update")
    public CommonResult<?> changeTag(@Validated @RequestBody TagUpdateRequest tagUpdateRequest) {
        tagService.updateTag(tagUpdateRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除标签信息")
    @DeleteMapping("/delete")
    public CommonResult<?> deleteTag(@RequestBody List<Long> ids) {
        if (ids == null && ids.size() == 0) {
            return CommonResult.fail("ids不能为空");
        }
        tagService.removeTag(ids);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取标签信息")
    @GetMapping("/getTag")
    public CommonResult<?> getTagById(Long id) {
        if (id == null) {
            return CommonResult.fail("id不能为空");
        }

        return CommonResult.success(tagService.getTagById(id));
    }


    @ApiOperation("分页查询标签信息")
    @PostMapping("/list/page")
    public CommonResult<?> listTagByPage(@Validated @RequestBody TagQueryRequest tagQueryRequest) {
        long current = tagQueryRequest.getCurrent();
        long size = tagQueryRequest.getPageSize();
        // 构造查询条件
        Page<TagEntity> tagPage = tagService.page(new Page<>(current, size), tagService.getQueryWrapper(tagQueryRequest));
        PageResult<TagEntity> pageResult = new PageResult<>();
        pageResult.setList(tagPage.getRecords());
        pageResult.setTotal(tagPage.getTotal());
        pageResult.setCurrent(current);
        pageResult.setSize(tagPage.getSize());
        pageResult.setPages(tagPage.getPages());
        return CommonResult.success(pageResult);
    }

    @ApiOperation("获取所有标签")
    @GetMapping
    public CommonResult<?> getTags(){
        return CommonResult.success(tagService.list());
    }
}
