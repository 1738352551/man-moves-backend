package cn.chenmanman.manmoviebackend.common.utils;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.activation.FileTypeMap;
import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common.utils
 * @className FileUploadUtils
 * @description  文件上传工具类
 * @date 2023/6/12 11:32
 */
@Scope("singleton")
@Slf4j
@Component
public class FileUploadUtils {

    @Value("${alibaba.accessKeyId}")
    private String accessKeyId;

    @Value("${alibaba.accessKeySecret}")
    private  String accessKeySecret;

    @Resource(type = OSSClient.class)
    private OSS ossClient;

    /**
     * 视频文件上传
     * @param title 视频名
     * @param fileName 文件名
     * @return 视频id
     * */
    public UploadStreamResponse uploadVideoFile(String title, String fileName, InputStream inputStream) {
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
//        request.setPartSize(2 * 1024 * 1024L);
//        request.setTaskNum(1); // 视频上传时的并发数
//        request.setEnableCheckpoint(true); // 开启断点续传
        /* 视频标签，多个用逗号分隔（可选） */
        //request.setTags("标签1,标签2");
        /* 视频描述（可选）*/
        //request.setDescription("视频描述");
        /* 封面图片（可选）*/
        //request.setCoverURL("http://cover.example.com/image_01.jpg");
        UploadVideoImpl uploader = new UploadVideoImpl();
        return uploader.uploadStream(request);
    }


    public DefaultAcsClient getClient() throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    public Map<String, Object> uploadFile(File file) throws IOException {
        List<String> imageTypes = Arrays.asList("jpg", "png", "jpeg");
        String fileExtend = FileTypeUtil.getType(file);
        boolean isImage = imageTypes.stream().anyMatch(types -> Objects.equals(types, fileExtend));
        String bucketName = "bucket-manman";
        String objectName = "files/other/";
        if (isImage) {
            objectName =  "files/images/" + DateUtil.format(new Date(), "yyyyMMdd") +"/"+ UUID.randomUUID().toString()+"."+fileExtend;
        } else {

            objectName =  objectName + DateUtil.format(new Date(), "yyyyMMdd") +"/"+ UUID.randomUUID().toString()+"."+fileExtend;
        }

        InputStream inputStream = Files.newInputStream(file.toPath());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        Map<String, Object> result = new HashMap<>();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        result.put("putObjectResult", ossClient.putObject(putObjectRequest));
        result.put("url", ossClient.generatePresignedUrl(bucketName, objectName,expiration).toString());
        return result;
    }

}
