package cn.chenmanman.manmoviebackend.pageprocessor.iqy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.pageprocessor.iqy
 * @className IqyVideoPageProcessor
 * @description TODO 爱奇艺视频爬取
 * @date 2023/5/13 1:14
 */
@Component
@Slf4j
public class IqyVideoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private final ConcurrentMap<String,Object> pageProcessorParams = new ConcurrentHashMap<>();
    private final AtomicInteger currentPage = new AtomicInteger(1); // 当前页

    public void addPageProcessorParam(String paramName, Object paramValue) {
        pageProcessorParams.put(paramName, paramValue);
    }

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {

        // https://mesh.if.iqiyi.com/portal/videolib/pcw/data


        // 列表json
        if (page.getUrl().regex("https://mesh.if.iqiyi.com/portal/videolib/pcw/data.*").match()) {

            log.debug("当前页第一个是: {} 当前页: {}", page.getJson().jsonPath("$.data[0].title"), currentPage);
            Map<String, Object> movieDetail = organizeData(page);
            page.putField("movieInfo", movieDetail);


            String isHasNextPage = page.getJson().jsonPath("$.has_next").toString(); // 是否有下一页
            if (isHasNextPage.equals("1")) {
                int nextPageIndex = currentPage.incrementAndGet();
                log.debug("iqy爬虫: 发现有下一页 {} ", nextPageIndex);
                Request request = new Request("https://mesh.if.iqiyi.com/portal/videolib/pcw/data?version=1.0&ret_num=30&page_id="+nextPageIndex+"&passport_id=&watch_list=4918580167510500,2362,90%3B3149002307316200,6543,3&channel_id=2&tagName=&mode=24&device_id=07d3c3aed6cb71b6622f2c7f7ea479fa");
                request.setMethod(HttpConstant.Method.GET);
                page.addTargetRequest(request);
            }
        }
    }

    /**
     * 数据整理
     * */
    private Map<String, Object> organizeData(Page page) {
        String title = page.getJson().jsonPath("$.data[*].title").toString();
        String desc = page.getJson().jsonPath("$.data[*].description").toString();
        String bannerUrl = page.getJson().jsonPath("$.data[*].image_url").toString();
        String entityId = page.getJson().jsonPath("$.data[*].firstId").toString();
        HashMap<String, Object> baseMovieInfo = new HashMap<>();
        baseMovieInfo.put("title", title);
        baseMovieInfo.put("desc", desc);
        baseMovieInfo.put("bannerUrl", bannerUrl);
        baseMovieInfo.put("entityId", entityId);
        Map<String, Map> movieDetails = getMovieDetails(entityId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("baseMovieInfo", baseMovieInfo);
        map.put("movieDetail", movieDetails);
        return map;
    }

    private Map<String, Map> getMovieDetails(String entityId) {
        Map<String, String> iqySign = IqyJsDecryption.getIqySign(Long.parseLong(entityId));
        String timestamp = iqySign.get("timestamp");
        String sign = iqySign.get("sign");
        String movieUrl = String.format("https://mesh.if.iqiyi.com/tvg/pcw/base_info?entity_id=%s&timestamp=%s&src=pcw_tvg&vip_status=0&vip_type=&auth_cookie=&device_id=07d3c3aed6cb71b6622f2c7f7ea479fa&user_id=&app_version=4.0.0&sign=%s", entityId, timestamp, sign);

        try (
                HttpResponse res = HttpRequest.get(movieUrl)
                        .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36 Edg/113.0.1774.35")
                        .execute();
                ) {

            int status = res.getStatus();
            if (status == HttpStatus.HTTP_OK) {
                String body = res.body();
                // 如果是电视剧
                Json json = new Json(body);
                List<String> page_keys = json.jsonPath("$.data.template.pure_data.selector_bk[0].videos.page_keys[*]").all();
                Map<String, Map> episodes= new HashMap<>();
                for (String pageKey : page_keys) {
                    HashMap<String, List> episodesMap = new HashMap<>();
                    List<String> episodeJsons = json.jsonPath("$.data.template.pure_data.selector_bk[0].videos.feature_paged['"+pageKey+"'][*]").all();
                    ArrayList<Map<String,String>> episodeList = new ArrayList<>();
                    for (String episodeJson : episodeJsons) {
                        Json json1 = new Json(episodeJson);
                        if (json1.get().contains("page_url")) {
                            HashMap<String, String> episodeMap = new HashMap<>();
                            String title = json1.jsonPath("$.title").toString();
                            String pageUrl = json1.jsonPath("$.page_url").toString();
                            episodeMap.put("title", title);
                            episodeMap.put("pageUrl", pageUrl);
                            episodeList.add(episodeMap);
                        }
                    }
                    // fixme
                    episodesMap.put(pageKey, episodeList);
                    episodes.put("episodes", episodesMap);
                }
                return episodes;
            }
        }
        return null;
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return site;
    }
}
