package cn.chenmanman.manmoviebackend.pageprocessor;

import cn.chenmanman.manmoviebackend.pageprocessor.iqy.IqyJsDecryption;
import cn.chenmanman.manmoviebackend.pageprocessor.iqy.IqyVideoPageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.Resource;
import javax.script.ScriptEngine;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.pageprocessor
 * @className TestIqyVideoPageProcessor
 * @description TODO
 * @date 2023/5/13 13:18
 */

@SpringBootTest
@Slf4j
public class TestIqyVideoPageProcessor {

    @Resource
    private IqyVideoPageProcessor iqyVideoPageProcessor;

    @Test
    public void testRunIqy(){
        Request request = new Request("https://mesh.if.iqiyi.com/portal/videolib/pcw/data?version=1.0&ret_num=30&page_id=1&passport_id=&watch_list=4918580167510500,2362,90%3B3149002307316200,6543,3&channel_id=2&tagName=&mode=24&device_id=07d3c3aed6cb71b6622f2c7f7ea479fa");
        request.setMethod(HttpConstant.Method.GET);
        request.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36 Edg/113.0.1774.35");
        Spider iqySpider = Spider.create(iqyVideoPageProcessor)
                .thread(5)
                .addRequest(request);
        iqySpider.run();
    }

    @Test
    public void testSign() {

        log.debug("计算出的sign: {}", IqyJsDecryption.getIqySign(2827333801193900L));
    }
}
