package cn.chenmanman.manmoviebackend.pageprocessor.tencent;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.pageprocessor
 * @className TencentVideoPageProcessor
 * @description 腾讯视频爬虫， todo 代码整理、改为电影电视剧通用
 * @date 2023/5/5 22:14
 */
@Slf4j
@Component
public class TencentVideoPageProcessor implements PageProcessor {


    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private final Map<String,Object> pageProcessorParams = new HashMap<>();


    public void addPageProcessorParam(String paramName, Object paramValue) {
        pageProcessorParams.put(paramName, paramValue);
    }


    @Override
    public void process(Page page) {
        String channelId = (String) pageProcessorParams.get("channelId");
        List<String> cids = new ArrayList<>();
        if (page.getUrl().regex("https://pbaccess.video.qq.com/trpc.vector_layout.page_view.PageService/getPage?(.*?)").match()) { // 如果有下一页
            String isHasNextPage = page.getJson().jsonPath("$.data.has_next_page").toString();
            Integer currentPage = Integer.parseInt(page.getJson().jsonPath("$.data.page_context.page_index").toString());
            log.debug("当前页: {}", currentPage);
            getTencentVideoList(page, currentPage);

            if (isHasNextPage.equals("true")) {
                Request request = new Request("https://pbaccess.video.qq.com/trpc.vector_layout.page_view.PageService/getPage?video_appid=3000010");
                request.setMethod(HttpConstant.Method.POST);
                request.setRequestBody(HttpRequestBody.json("{\"page_context\":{\"page_index\":\""+(currentPage)+"\"},\"page_params\":{\"page_id\":\"channel_list_second_page\",\"page_type\":\"operation\",\"channel_id\":\""+channelId+"\",\"filter_params\":\"ifeature=2&iarea=-1&iyear=-1&ipay=-1&sort=75\",\"page\":\"0\"},\"page_bypass_params\":{\"params\":{\"page_id\":\"channel_list_second_page\",\"page_type\":\"operation\",\"channel_id\":\""+channelId+"\",\"filter_params\":\"ifeature=2&iarea=-1&iyear=-1&ipay=-1&sort=75\",\"page\":\"0\",\"caller_id\":\"3000010\",\"platform_id\":\"2\",\"data_mode\":\"default\",\"user_mode\":\"default\"},\"scene\":\"operation\",\"abtest_bypass_id\":\"0059c37da148261e\"}}", "utf-8"));
                page.addTargetRequest(request);
                log.debug("当前爬取页面 - {} - {}", currentPage, isHasNextPage);
            }
        }


        log.debug("爬到 {}", page.getResultItems());

    }

    private List<String> getTencentVideoList(Page page, Integer currentPage) {
        List<String> videoList = null;
        if (currentPage > 1) {
            videoList = page.getJson().jsonPath("$.data.CardList[0].children_list.list.cards.*").all();
        } else {
            videoList = page.getJson().jsonPath("$.data.CardList[1].children_list.list.cards.*").all();

        }


        List<Map<String, Object>> movieInfoList = new ArrayList<>();
        for (String videoJson : videoList) {
            Map<String, Object> map = new HashMap<>();
            String title = new Json(videoJson).jsonPath("$.params.title").toString();
            String content = new Json(videoJson).jsonPath("$.params.second_title").toString();
            String cid = new Json(videoJson).jsonPath("$.params.cid").toString();
            String bannerUrl = new Json(videoJson).jsonPath("$.params.new_pic_hz").toString();
            map.put("title", title);
            map.put("content", content);
            map.put("cid", cid);
            map.put("banner_url", bannerUrl);

            // 请求视频分集接口
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36 Edg/112.0.1722.68");
            headers.put("referer", "https://v.qq.com/");
            headers.put("cookie", TxCookies.getCookies());
            String result = HttpRequest.post("https://pbaccess.video.qq.com/trpc.universal_backend_service.page_server_rpc.PageServer/GetPageData?video_appid=3000010&vplatform=2")
                    .addHeaders(headers)
                    .body("{\"page_params\":{\"req_from\":\"web_vsite\",\"page_id\":\"vsite_episode_list\",\"page_type\":\"detail_operation\",\"id_type\":\"1\",\"page_size\":\"60\",\"cid\":\"" + cid + "\",\"vid\":\"\",\"lid\":\"\",\"page_num\":\"\",\"page_context\":\"\",\"detail_page_type\":\"0\"},\"has_cache\":1}")
                    .executeAsync().body();
            List<Map<String, Object>> episodeInfoList = saveTencentVideoInfo(result, page);
            map.put("movie_episodes", episodeInfoList);
            movieInfoList.add(map);
        }
        page.putField("movie_info", movieInfoList);
        return videoList.stream().map(s -> new Json(s).jsonPath("$.params.cid").toString()).collect(Collectors.toList());
    }

    private List<Map<String, Object>> saveTencentVideoInfo(String json, Page page) {
        List<String> episodes = new Json(json).jsonPath("$.data.module_list_datas[0].module_datas[0].item_data_lists.item_datas[*]").all();
        List<Map<String, Object>> episodeInfoList = new ArrayList<>();
        for (String episode:
             episodes) {
            Map<String, Object> map = new HashMap<>();
            String vid = new Json(episode).jsonPath("$.item_params.vid").toString();
            String cid = new Json(episode).jsonPath("$.item_params.cid").toString();
            String union_title = new Json(episode).jsonPath("$.item_params.union_title").toString();
            String movieUrl = "https://v.qq.com/x/cover/"+cid+"/"+vid+".html";
            map.put("vid", vid);
            map.put("cid", cid);
            map.put("movieUrl", movieUrl);
            map.put("unionTitle", union_title);
            episodeInfoList.add(map);
        }
        return episodeInfoList;
    }

    @Override
    public Site getSite() {
        return site;
    }

}
