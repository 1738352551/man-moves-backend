package cn.chenmanman.manmoviebackend.pageprocessor.tencent;

import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.Date;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common.utils
 * @className TxJsDecryption
 * @description 腾讯加密算法
 * @date 2023/5/10 10:26
 */
@Slf4j
public class TxJsDecryption {
    private static ScriptEngine cKeyEngine = new ScriptEngineManager().getEngineByName("js");
    private static ScriptEngine vstkEngine = new ScriptEngineManager().getEngineByName("js");

    static {
        try {
            cKeyEngine.eval(new FileReader("src/main/resources/js/tencent/cKey.js"));
            vstkEngine.eval(new FileReader("src/main/resources/js/tencent/g_vstk.js"));
        } catch (Exception e) {
            log.error("腾讯加密算法初始化失败");
        }
    }

    public static String getcKey(String vid, String playUrl) {
        try {
            return (String) ((Invocable) cKeyEngine)
                    .invokeFunction("getcKey"
                            , vid
                            , String.valueOf(new Date().getTime()).substring(0, 10)
                            , "3.5.57"
                            , TxCookies.getValue("video_guid")
                            , "10201"
                            , playUrl);
        } catch (Exception e) {
            log.error("获取getcKey失败");
            return "";
        }
    }

    /**
     *
     * @param vusession vqq_vusession
     * @return
     */
    public static String getgVstk(String vusession) {
        try {
            return (String) ((Invocable) vstkEngine)
                    .invokeFunction("getgVstk"
                            , vusession);
        } catch (Exception e) {
            log.error("获取g_vstk失败");
            return "";
        }
    }
}
