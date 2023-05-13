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
            organizeData(page);



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
        String entityId = page.getJson().jsonPath("$.data[*].prevue.entity_id").toString();
        HashMap<String, Object> baseMovieInfo = new HashMap<>();
        baseMovieInfo.put("title", title);
        baseMovieInfo.put("desc", desc);
        baseMovieInfo.put("bannerUrl", bannerUrl);
        baseMovieInfo.put("entityId", entityId);

        getMovieDetails(entityId);

        return null;
    }

    private void getMovieDetails(String entityId) {
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
                List<String> page_keys = new Json(body).jsonPath(".data.template.pure_data.selector_bk[0].videos.page_keys[*]").all();
                for (String pageKey : page_keys) {
                    List<Map<String, String>> episodes= new ArrayList<>();
//                    .data.template.pure_data.selector_bk[0].videos.feature_paged[pageKey][*].title
                    // todo 剧集 剧集
                }

                log.debug("获得详细信息: {}", body);

            }
        }

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
    /**
     * https://mesh.if.iqiyi.com/portal/videolib/pcw/data?
     * version=1.0
     * &ret_num=30
     * &page_id=3
     * 可以去掉&device_id=07d3c3aed6cb71b6622f2c7f7ea479fa
     * &passport_id=
     * 可以去掉&recent_search_query=%E4%BB%96%E6%98%AF%E8%B0%81%3B%E5%BC%A0%E8%AF%91%3B%E8%AD%A6%E5%8C%AA%E7%94%B5%E8%A7%86%E5%89%A7%3B%3B%E6%98%BE%E5%BE%AE%E9%95%9C
     * 可以去掉&ip=202.108.14.240
     * &channel_id=2
     * &tagName=&mode=24
     * 电视剧的全部是&channel_id=2
     * 电影的全部是&channel_id=1
     * */

    /**
     * https://mesh.if.iqiyi.com/portal/videolib/pcw/data?
     * version=1.0
     * &ret_num=30
     * &page_id=2
     * &device_id=07d3c3aed6cb71b6622f2c7f7ea479fa
     * &passport_id=
     * &recent_selected_tag=%E7%BB%BC%E5%90%88
     * &recent_search_query=%E4%BB%96%E6%98%AF%E8%B0%81%3B%E5%BC%A0%E8%AF%91%3B%E8%AD%A6%E5%8C%AA%E7%94%B5%E8%A7%86%E5%89%A7%3B%3B%E6%98%BE%E5%BE%AE%E9%95%9C
     * &ip=202.108.14.240
     * &channel_id=1&tagName=&mode=24
     * */

    /*
    * https://t7z.cupid.iqiyi.com/show2?e=AF48RQoAFkBeYgAWRVwMXAUXL0IKHx9HRigfWUFZSF1fADBcH0ZvRgkzAQRAQgNdAk03RV1cD1JaKwxcWUNFaxwCNl8cV0JVVCtYXldvQ1UfCGxuVBxdQgFvHVdCVVRAGA04bkJVU1FdMwJvURxWRhQGK1heV29CVDxQXFwCblNdBC1UVURZXlYAQ1FeWwBrF085WFxEVUJuOx1XQlVUQBgNOG5CUV5bBQBTHFNfX0AUDStuUxxTUUU6Vl9CSW5YGAEAU28BHFdDOlREWV5WawMCMVoCb1UcXTZCRG9fX1gYDToXVQ0ECFI7CAhRUwAEEAZvVQlVUgYBbVIFCVEAVxQFbwVSUhZDRSZBVQ0CF0IXETIMU1FEVVYwQ0lvXFhWX1J5R1ZCXVJdNAxjFQNwWBgBAABvRllUVDAXRlZCXEYCF2JiFQNxXFg9bgFvRlhQFAwAV1xfUURuKVhUVV9uVQMGPgMDFkVAVWIBFkBEWFBMU24BAQAAAwBvAAAAAAEEQVNvAQAWUg0CbgUJAAADB0FUbAAGAgAAFzMMAwEECARBUWwBBwMBBgNvARZbDQASB15vF0BGDRZBLQwAFlNHCUdNbAIeAh1RQzpCBhZDRwlHTWwCHgIdUUM6QgYWV0UJQEUvUg0WVVEMbhdGVVlVCRdSa1AHU1RSVG0DBAcJVQAUWjwGBVIJAlBmVwMDVQkAVxMsDAA%3D&h=1683951832740&a=qc_100001_100014&u=07d3c3aed6cb71b6622f2c7f7ea479fa&p=&s=accb94c98bc934fd0af3a1a0389c817b&cb=jQuery1124015168879360534238_1683951832722&_=1683951832723
    * https://pcw-api.iqiyi.com/video/video/playervideoinfo?tvid=3149002307316200&locale=cn_s&callback=Q4e85ca7f5bf1cb8f1ec58f327be166c4
    *
    * 1032888801146000
    * 视频信息：
    * https://mesh.if.iqiyi.com/tvg/pcw/base_info
    * ?entity_id=3149002307316200
    * &timestamp=1683951833126
    * &src=pcw_tvg
    * &vip_status=0
    * &vip_type=
    * &auth_cookie=
    * &device_id=07d3c3aed6cb71b6622f2c7f7ea479fa
    * &user_id=
    * &app_version=4.0.0
    * &sign=F89BA467F7BFB5666795C41588FFDC50
    *
    *
    * https://mesh.if.iqiyi.com/player/pcw/video/baseInfo?ip=&id=1032888801146000
    * */
}
