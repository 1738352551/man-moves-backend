package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tencent.TencentMoviePullPostRequest;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPageProcessor;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPipeline;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.mapper.MovieInfoMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 17383
 * @description 针对表【movie_info】的数据库操作Service实现
 * @createDate 2023-05-05 15:28:18
 */
@Service
public class MovieInfoServiceImpl extends ServiceImpl<MovieInfoMapper, MovieInfoEntity>
        implements MovieInfoService {
    @Resource
    private TencentVideoPageProcessor tencentVideoPageProcessor;

    @Resource
    private TencentVideoPipeline tencentVideoPipeline;


    private Spider tencentSpider;


    public void tencentMoviesPullStop() {
        if (tencentSpider == null || tencentSpider.getStatus().compareTo(Spider.Status.Init) == 0) {
            throw new BusinessException("腾讯视频爬虫未启动!", 500L);
        } else if (tencentSpider.getStatus().compareTo(Spider.Status.Stopped) == 0) {
            throw new BusinessException("腾讯视频爬虫已停止!", 500L);
        }
        tencentSpider.stop();
    }

    /**
     * @param tencentMoviePullPostRequest 腾讯视频抓取Post请求参数
     * @description 腾讯视频抓取
     * @createDate 2023年5月12日 22点41分
     */
    @Override
    public void tencentMoviesPullRun(TencentMoviePullPostRequest tencentMoviePullPostRequest) {
        // 不允许重复创建实例
        if (tencentSpider == null) {
            // 1. 创建腾讯视频爬虫实例
            Request request = new Request("https://pbaccess.video.qq.com/trpc.vector_layout.page_view.PageService/getPage?video_appid=3000010");
            request.setMethod(HttpConstant.Method.POST);

            String channelId = "100173";
            if (tencentMoviePullPostRequest.getType().equals("1001")) {
                channelId = "100173";
            } else if (tencentMoviePullPostRequest.getType().equals("1002")) {
                channelId = "100113";

            }
            request.setRequestBody(HttpRequestBody.json("{\"page_context\":{\"page_index\":\"0\"},\"page_params\":{\"page_id\":\"channel_list_second_page\",\"page_type\":\"operation\",\"channel_id\":\"" + channelId + "\",\"filter_params\":\"ifeature=2&iarea=-1&iyear=-1&ipay=-1&sort=75\",\"page\":\"0\"},\"page_bypass_params\":{\"params\":{\"page_id\":\"channel_list_second_page\",\"page_type\":\"operation\",\"channel_id\":\"" + channelId + "\",\"filter_params\":\"ifeature=2&iarea=-1&iyear=-1&ipay=-1&sort=75\",\"page\":\"0\",\"caller_id\":\"3000010\",\"platform_id\":\"2\",\"data_mode\":\"default\",\"user_mode\":\"default\"},\"scene\":\"operation\",\"abtest_bypass_id\":\"0059c37da148261e\"}}", "utf-8"));
            tencentVideoPageProcessor.addPageProcessorParam("channelId", channelId);
            tencentSpider = Spider.create(tencentVideoPageProcessor)
                    .addRequest(request)
                    .addPipeline(tencentVideoPipeline)
                    .thread(5);


            if (tencentSpider == null) {
                throw new BusinessException("腾讯视频爬虫创建实例失败!", 500L);
            }
        }

        // 不允许在爬虫运行的时候再次运行
        if (tencentSpider.getStatus().compareTo(Spider.Status.Running) == 0) {
            throw new BusinessException("腾讯视频爬虫正在运行!", 500L);
        }
        tencentSpider.runAsync();

    }
}




