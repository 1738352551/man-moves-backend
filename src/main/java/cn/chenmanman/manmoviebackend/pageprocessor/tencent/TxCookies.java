package cn.chenmanman.manmoviebackend.pageprocessor.tencent;

import cn.chenmanman.manmoviebackend.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common.utils
 * @className TxCookies
 * @description TODO
 * @date 2023/5/10 10:21
 */
@Slf4j
public class TxCookies {
    private static String cookies = "";

    private static Map<String, String> cookiesMap = new HashMap<String, String>();

    private static void dataInit() {
        // 更换会员账号的cookies请改这里 请复制完整cookie值
        cookies = "pgv_pvid=352542520; video_platform=2; ptcz=d536a6082057b1226a6178f4b6703259efcc2d49a67db8587c928a0147c816ab; o_cookie=1738352551; fqm_pvqid=e6b51296-4b08-475a-85a3-9ee3fd3f3ad0; RK=x9nJvsOddi; tvfe_boss_uuid=ed0007ccde6f3392; video_guid=0059c37da148261e; pac_uid=1_1738352551; eas_sid=j1a6f7h9q7X5k7q6A2m7J4o6p4; ptui_loginuin=3553854191; iip=0; pgv_info=ssid=s3156108220; vversion_name=8.2.95; video_omgid=0059c37da148261e; _qpsvr_localtk=0.29970973079395113; _video_qq_login_time_init=1683558591; login_time_last=2023-5-8 23:10:2; lolqqcomrouteLine=tftguide_tftguide";
        cookiesMap.clear();
        for (String item : cookies.split(";")) {
            String[] list = item.split("=");
            if (list != null && list.length == 2) {
                cookiesMap.put(list[0].replaceAll(" ", ""), list[1]);
            }
        }
    }

    static {
        dataInit();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    String url = "https://access.video.qq.com/user/auth_refresh";
                    String gVstk = TxJsDecryption.getgVstk(getValue("vqq_vusession"));
                    String param = "vappid=11059694&vsecret=fdf61a6be0aad57132bc5cdf78ac30145b6cd2c1470b0cfe&type=qq&g_vstk=" + gVstk;

                    Map resHeads = new HashMap();

                    String res = HttpUtil.sendGet(url + "?" + param
                            , new HashMap() {{ put("cookie", getCookies()); put("referer", "https://v.qq.com/"); }}
                            , resHeads);

                    if (res != null && res.indexOf("succ") != -1) {
                        List<String> cookieList = (List) resHeads.get("Set-Cookie");
                        for (int i = 0; i < cookieList.size(); i++) {
                            String[] strList = cookieList.get(i).split(";");
                            strList = strList[0].split("=");
                            if (strList.length == 2) {
                                setValue(strList[0], strList[1]);
                            }
                        }
                    }

                    log.debug( "cookies已更新：" + getCookies());

                    try {
                        Thread.sleep(300000);
                    } catch (InterruptedException e) {
                        log.error("更新cookies失败");
                    }
                }
            }
        }.start();
    }

    public static String getCookies() {
        return cookies;
    }

    public static void setCookies(String cookies) {
        TxCookies.cookies = cookies;
    }

    public static String getValue(String key) {
        String value = cookiesMap.get(key);
        return value == null ? "" : value;
    }

    public static void setValue(String key, String value) {
        rmValue(key);

        if (!StringUtils.hasText(cookies.replaceAll(" ", ""))) {
            cookies += "; ";
        }

        cookies += key + "=" + value;
        cookiesMap.put(key, value);
    }

    public static void rmValue(String key) {
        if (!cookiesMap.containsKey(key)) {
            return;
        }

        String[] cookieList = cookies.replaceAll("; ", ";").split(";");

        String str = "";
        for (String item : cookieList) {
            if (item.length() > key.length() && key.equals(item.substring(0, key.length()))) {
                continue;
            }

            if (str != "") {
                str += "; ";
            }

            str += item;
        }

        cookies = str;

        cookiesMap.remove(key);
    }
}
