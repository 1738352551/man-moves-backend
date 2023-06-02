package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.actor.ActorUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.vo.movie.EpisodesVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.movie.ActorEntity;
import cn.chenmanman.manmoviebackend.service.ActorService;
import cn.chenmanman.manmoviebackend.mapper.ActorMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
* @author 17383
* @description 针对表【actor】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class ActorServiceImpl extends ServiceImpl<ActorMapper, ActorEntity>
    implements ActorService{

    /**
     * @param actorAddRequest 演员信息添加请求参数
     * @description 添加演员信息
     */
    @Override
    public void addActor(ActorAddRequest actorAddRequest) {
        ActorEntity actorEntity = new ActorEntity();
        BeanUtils.copyProperties(actorAddRequest, actorEntity);
        this.baseMapper.insert(actorEntity);
    }

    /**
     * @param actorUpdateRequest 演员信息修改请求
     * @description 修改演员信息
     */
    @Override
    public void updateActor(ActorUpdateRequest actorUpdateRequest) {
        ActorEntity actorEntity = this.baseMapper.selectById(actorUpdateRequest.getId());
        Optional.ofNullable(actorEntity)
                .orElseThrow(() -> new BusinessException(String.format("需要编辑的演员信息不存在!, id: %d", actorUpdateRequest.getId()), 500L));
        BeanUtils.copyProperties(actorUpdateRequest, actorEntity);
        this.baseMapper.updateById(actorEntity);
    }

    /**
     * @param ids 演员id列表
     * @description 删除演员信息
     */
    @Override
    public void removeActor(List<Long> ids) {
        ids.forEach(id -> {
            ActorEntity actorEntity = this.baseMapper.selectById(id);
            Optional.ofNullable(actorEntity)
                    .orElseThrow(() -> new BusinessException(String.format("演员不存在, 演员id: %d", id), 500L));
            this.baseMapper.deleteById(id);
        });
    }

    /**
     * @param id 演员id
     * @description 根据id获取演员信息
     */
    @Override
    public ActorEntity getActorById(Long id) {
        ActorEntity actorEntity = this.baseMapper.selectById(id);
        Optional.ofNullable(actorEntity)
                .orElseThrow(() -> new BusinessException(String.format("影视剧集不存在!, id: %d", id), 500L));
        return actorEntity;
    }

    /**
     * @param actorQueryRequest 演员查询请求
     * @description 分页查询的条件
     */
    @Override
    public LambdaQueryWrapper<ActorEntity> getQueryWrapper(ActorQueryRequest actorQueryRequest) {
        // 检查查询条件是否为null
        Optional.ofNullable(actorQueryRequest).orElseThrow(() -> new BusinessException("请求参数不能为空", 500L));
        return new LambdaQueryWrapper<ActorEntity>()
                .like(Strings.isNotBlank(actorQueryRequest.getName()),ActorEntity::getName, actorQueryRequest.getName());
    }
}




