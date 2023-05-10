package cn.chenmanman.manmoviebackend.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className TestMediaUploadService
 * @description TODO
 * @date 2023/5/5 20:19
 */
@SpringBootTest
@Slf4j
public class TestMediaUploadService {

    @Resource
    private OSS ossClient;

    @Test
    public void testUpload() {
        String bucketName = "bucket-manman";
        String objectName = "text/exampleobject.txt";
        try {
            String content = "Hello OSS";
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        } catch (OSSException oe) {
            log.warn("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.warn("Error Message:" + oe.getErrorMessage());
            log.warn("Error Code:" + oe.getErrorCode());
            log.warn("Request ID:" + oe.getRequestId());
            log.warn("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.warn("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.warn("Error Message: {} - {}", ce.getMessage(), ce.getCause());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
