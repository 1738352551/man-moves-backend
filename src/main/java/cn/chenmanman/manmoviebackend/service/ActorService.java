package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.ActorEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17383
* @description 针对表【actor】的数据库操作Service
* @createDate 2023-05-05 15:28:18
*/
public interface ActorService extends IService<ActorEntity> {

    /**
     * @description 添加演员信息
     * */
    void addActor(ActorAddRequest actorAddRequest);

    /**
     * @description 修改演员信息
     * */
    void updateActor(ActorUpdateRequest actorUpdateRequest);

    /**
     * @description 删除演员信息
     * */
    void removeActor(List<Long> ids);

    /**
     * @description 根据id获取演员信息
     * */
    ActorEntity getActorById(Long id);

    /**
     * @description 分页查询的条件
     * */
    LambdaQueryWrapper<ActorEntity> getQueryWrapper(ActorQueryRequest actorQueryRequest);
}
