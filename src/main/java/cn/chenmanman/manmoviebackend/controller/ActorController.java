package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.aop.Limit;
import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.ActorEntity;
import cn.chenmanman.manmoviebackend.service.ActorService;
import cn.chenmanman.manmoviebackend.service.impl.ActorServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className ActorController
 * @description 演员接口
 * @date 2023/6/2 23:48
 */
@RequestMapping("/actors")
@RestController
@Api(tags = "演员接口")
public class ActorController {

    @Resource(type = ActorServiceImpl.class)
    private ActorService actorService;

    @ApiOperation(value = "添加演员")
    @PostMapping("/add")
    public CommonResult<?> addActor(@Validated @RequestBody ActorAddRequest ActorAddRequest) {
        actorService.addActor(ActorAddRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改演员信息")
    @PostMapping("/update")
    public CommonResult<?> changeActor(@Validated @RequestBody ActorUpdateRequest ActorUpdateRequest) {
        actorService.updateActor(ActorUpdateRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除演员信息")
    @DeleteMapping("/delete")
    public CommonResult<?> deleteActor(@RequestBody List<Long> ids) {
        if (ids == null && ids.size() == 0) {
            return CommonResult.fail("ids不能为空");
        }
        actorService.removeActor(ids);
        return CommonResult.success();
    }


    @Limit(key = "limit3", permitsPerSecond = 2, timeout = 500, timeunit = TimeUnit.MILLISECONDS, msg = "系统繁忙，请稍后再试！")
    @ApiOperation(value = "获取演员信息")
    @GetMapping("/getActor")
    public CommonResult<?> getActorById(Long id) {
        if (id == null) {
            return CommonResult.fail("id不能为空");
        }

        return CommonResult.success(actorService.getActorById(id));
    }


    @Limit(key = "limit3", permitsPerSecond = 2, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "系统繁忙，请稍后再试！")
    @ApiOperation("分页查询演员信息")
    @PostMapping("/list/page")
    public CommonResult<?> listActorByPage(@Validated @RequestBody ActorQueryRequest ActorQueryRequest) {
        long current = ActorQueryRequest.getCurrent();
        long size = ActorQueryRequest.getPageSize();
        // 构造查询条件
        Page<ActorEntity> movieInfoPage = actorService.page(new Page<>(current, size), actorService.getQueryWrapper(ActorQueryRequest));
        return CommonResult.success(movieInfoPage);
    }
}
