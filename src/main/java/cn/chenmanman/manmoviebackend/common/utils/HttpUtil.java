package cn.chenmanman.manmoviebackend.common.utils;

import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common.utils
 * @className HttpUtil
 * @description TODO
 * @date 2023/5/10 10:23
 */
public class HttpUtil {
    static {
        try {
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(
                    new HostnameVerifier() {
                        public boolean verify(String urlHostName, SSLSession session) { return true; }
                    });
        } catch (Exception e)  {}
    }

    /**
     * 发送GET请求
     *
     * @param url
     *            目的地址
     * @return 远程响应结果
     */
    public static String sendGet(String url) {
        return sendGet(url, null, null);
    }

    /**
     * 发送GET请求
     *
     * @param url
     *            目的地址
     * @param heads
     *            请求头，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> heads) {
        return sendGet(url, heads, null);
    }

    /**
     * 发送GET请求
     *
     * @param url
     *            目的地址
     * @param heads
     *            请求头，Map类型。
     * @param resHeaders
     *            接收响应头，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> heads, Map<String, List<String>> resHeaders) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        try {
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            // 设置自定义请求头
            if (heads != null && heads.size() > 0){
                for (Map.Entry<String, String> ent : heads.entrySet()) {
                    httpConn.setRequestProperty(ent.getKey(), ent.getValue());
                }
            }
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            if (resHeaders != null) resHeaders.putAll(httpConn.getHeaderFields());
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @return 远程响应结果
     */
    public static String sendPost(String url) {
        return sendPost(url, null, null, null);
    }

    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, String parameters) {
        return sendPost(url, parameters, null, null);
    }

    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @return 远程响应结果
     */
    public static String sendPost(String url, String parameters, Map<String, String> heads) {
        return sendPost(url, parameters, heads, null);
    }

    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @param heads
     *            请求头，Map类型。
     * @param resHeaders
     *            接收响应头，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, String parameters, Map<String, String> heads, Map<String, List<String>> resHeaders) {
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer buf = new StringBuffer();// 处理请求参数
        try {
//            if (!StringUtil.isEmpty(parameters)) parameters = URLEncoder.encode(parameters, "UTF-8"); //参数编码
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            // 设置自定义请求头
            if (heads != null && heads.size() > 0){
                for (Map.Entry<String, String> ent : heads.entrySet()) {
                    httpConn.setRequestProperty(ent.getKey(), ent.getValue());
                }
            }
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            if (!StringUtils.hasText(parameters)) out.write(parameters);
            // flush输出流的缓冲
            out.flush();
            // 响应头部获取
            if (resHeaders != null) resHeaders.putAll(httpConn.getHeaderFields());
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                buf.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return buf.toString();
    }


    public static String sendPostJson(String url, String json, Map<String, String> heads, Map<String, List<String>> resHeaders) {
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer buf = new StringBuffer();// 处理请求参数
        try {
//            if (!StringUtil.isEmpty(parameters)) parameters = URLEncoder.encode(parameters, "UTF-8"); //参数编码
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            // 设置自定义请求头
            if (heads != null && heads.size() > 0){
                for (Map.Entry<String, String> ent : heads.entrySet()) {
                    httpConn.setRequestProperty(ent.getKey(), ent.getValue());
                }
            }
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            if (!StringUtils.hasText(json)) out.write(json);
            // flush输出流的缓冲
            out.flush();
            // 响应头部获取
            if (resHeaders != null) resHeaders.putAll(httpConn.getHeaderFields());
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                buf.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return buf.toString();
    }


    /**
     * 访问对象
     * @param url   目的地址
     * @return
     */
    public static byte[] sendGetObject(String url) {
        return sendGetObject(url, null);
    }

    /**
     * 访问对象
     * @param url   目的地址
     * @param resHeaders
     *            接收响应头，Map类型。
     * @return
     */
    public static byte[] sendGetObject(String url, Map<String, List<String>> resHeaders) {
        BufferedInputStream in = null;// 读取响应输入流
        byte[] buf = new byte[1024 * 1024 * 5];
        try {
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置自定义请求头
//            if (heads != null && heads.size() > 0){
//                for (Map.Entry<String, String> ent : heads.entrySet()) {
//                    httpConn.setRequestProperty(ent.getKey(), ent.getValue());
//                }
//            }
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            if (resHeaders != null) resHeaders.putAll(httpConn.getHeaderFields());
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedInputStream(httpConn.getInputStream());
            byte[] line = new byte[1024 * 1024];
            int size = 0;
            int len = -1;
            // 读取返回的内容
            while ((len = in.read(line)) != -1) {
                size += len;
                if (size > buf.length) buf = Arrays.copyOf(buf, buf.length + (1024 * 1024 * 5));
                System.arraycopy(line, 0, buf, size - len, len);
            }
            buf = Arrays.copyOf(buf, size);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return buf;
    }

    private static void trustAllHttpsCertificates()
            throws Exception
    {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new TrustAllManager();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(
                sc.getSocketFactory());
    }

    private static class TrustAllManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() { return null; }
        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException { }
        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException { }
    }
}
