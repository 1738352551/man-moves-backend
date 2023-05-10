package cn.chenmanman.manmoviebackend.pageprocessor;

import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPageProcessor;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPipeline;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.Resource;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.pageprocessor
 * @className TestTencentVideoPageProcessor
 * @description 测试腾讯视频爬取
 * @date 2023/5/6 16:38
 */
@SpringBootTest
@Slf4j
public class TestTencentVideoPageProcessor {
    @Resource
    private TencentVideoPipeline tencentVideoPipeline;

    @Resource
    private TencentVideoPageProcessor tencentVideoPageProcessor;

    @Test
    public void webMagicTest() {
        Request request = new Request("https://pbaccess.video.qq.com/trpc.vector_layout.page_view.PageService/getPage?video_appid=3000010");
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.json("{\"page_context\":{\"page_index\":\"0\"},\"page_params\":{\"page_id\":\"channel_list_second_page\",\"page_type\":\"operation\",\"channel_id\":\"100113\",\"filter_params\":\"ifeature=2&iarea=-1&iyear=-1&ipay=-1&sort=75\",\"page\":\"0\"},\"page_bypass_params\":{\"params\":{\"page_id\":\"channel_list_second_page\",\"page_type\":\"operation\",\"channel_id\":\"100113\",\"filter_params\":\"ifeature=2&iarea=-1&iyear=-1&ipay=-1&sort=75\",\"page\":\"0\",\"caller_id\":\"3000010\",\"platform_id\":\"2\",\"data_mode\":\"default\",\"user_mode\":\"default\"},\"scene\":\"operation\",\"abtest_bypass_id\":\"0059c37da148261e\"}}", "utf-8"));
        Spider.create(tencentVideoPageProcessor)
//                .addUrl("https://www.zhihu.com/api/v3/feed/topstory/recommend?action=down&ad_interval=-10&after_id=23&desktop=true&page_number=5&session_token=9e0529689007593617f855da62d3d7f4")
                .addRequest(request)
                .addPipeline(tencentVideoPipeline)
                .thread(5).run();
    }
}
