package cn.chenmanman.manmoviebackend.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.config
 * @className OssConfig
 * @description 阿里巴巴oss对象存储
 * @date 2023/5/5 18:20
 */
@Configuration
public class OssConfig {
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。

    @Value("${alibaba.endpoint}")
    private String endpoint;

    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    @Value("${alibaba.accessKeyId}")
    private String accessKeyId;

    @Value("${alibaba.accessKeySecret}")
    private String accessKeySecret;


    // 填写Bucket名称，例如examplebucket。
//    @Value("alibaba.bucketName")
//    private String bucketName ;
    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }


}
