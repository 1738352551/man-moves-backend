package cn.chenmanman.manmoviebackend.pageprocessor.iqy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.pageprocessor.iqy
 * @className IqyJsDescryption
 * @description TODO
 * @date 2023/5/13 16:52
 */
@Slf4j
public class IqyJsDecryption {
    private static ScriptEngine iqyMd5Engine = new ScriptEngineManager().getEngineByName("js");

    static {
        try {
            iqyMd5Engine.eval(new FileReader("src/main/resources/js/iqy/iqy_md5.js"));;
        } catch (Exception e) {
            log.error("爱奇艺加密算法初始化失败");
        }
    }



    public static Map<String, String> getIqySign(Long entityId) {
        try {
            String md5AndTime = (String) ((Invocable) iqyMd5Engine)
                    .invokeFunction("getMd5", entityId);
            HashMap<String, String> md5AndTimeMap = new HashMap<>();
            String[] split = md5AndTime.split("\\|");
            md5AndTimeMap.put("sign", split[0]);
            md5AndTimeMap.put("timestamp", split[1]);
            return md5AndTimeMap;
        } catch (Exception e) {
            log.error("获取IqySign失败");
            return new HashMap<>();
        }
    }
}
