package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.spider.tencent.TencentMoviePullPostRequest;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className MovieSpiderService
 * @description 视频采集爬虫服务
 * @date 2023/5/29 12:42
 */
public interface MovieSpiderService {
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
