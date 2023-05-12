package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tencent.TencentMoviePullPostRequest;
import cn.chenmanman.manmoviebackend.domain.entity.MovieInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author 17383
* @description 针对表【movie_info】的数据库操作Service
* @createDate 2023-05-05 15:28:18
*/
public interface MovieInfoService extends IService<MovieInfoEntity> {


    /**
     * @description 腾讯视频抓取停止
     * @createDate 2023年5月12日 22点40分
     * */
    public void tencentMoviesPullStop();

    /**
     * @description 腾讯视频抓取
     * @createDate 2023年5月12日 22点41分
     */
    public void tencentMoviesPullRun(TencentMoviePullPostRequest tencentMoviePullPostRequest);

}
