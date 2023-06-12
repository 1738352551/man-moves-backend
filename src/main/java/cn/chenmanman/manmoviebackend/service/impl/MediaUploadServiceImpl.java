package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.utils.FileUploadUtils;
import cn.chenmanman.manmoviebackend.domain.entity.Files;
import cn.chenmanman.manmoviebackend.mapper.FilesMapper;
import cn.chenmanman.manmoviebackend.service.MediaUploadService;
import cn.hutool.core.io.FileTypeUtil;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service.impl
 * @className MediaUploadServiceImpl
 * @description 媒资服务
 * @date 2023/6/12 12:02
 */
@Service
@Slf4j
public class MediaUploadServiceImpl implements MediaUploadService {
    @Resource
    private FileUploadUtils fileUploadUtils;

    @Resource
    private FilesMapper filesMapper;

    @Override
    public String uploadVideo(MultipartFile file) {
        List<GetPlayInfoResponse.PlayInfo> playInfoList = null;
        try {
            UploadStreamResponse uploadVideoResponse = fileUploadUtils.uploadVideoFile(file.getName(), file.getOriginalFilename(), file.getInputStream());
            if (uploadVideoResponse.isSuccess()) {
                GetPlayInfoRequest request = new GetPlayInfoRequest();
                request.setVideoId(uploadVideoResponse.getVideoId());
                DefaultAcsClient defaultAcsClient = fileUploadUtils.getClient();
                GetPlayInfoResponse acsResponse = defaultAcsClient.getAcsResponse(request);
                playInfoList = acsResponse.getPlayInfoList();


            }
        } catch (ClientException | IOException e) {
            throw new RuntimeException(e);
        }
        return !Collections.isEmpty(playInfoList) ? playInfoList.get(0).getPlayURL(): null;
    }

    @Override
    @Transactional
    public Files uploadFile(MultipartFile file) {
        Map<String,Object> result= null;
        Files files = null;
        try {
            File tempFile = File.createTempFile("man", "mao");
            file.transferTo(tempFile);
            result= fileUploadUtils.uploadFile(tempFile);
            if (((PutObjectResult)result.get("putObjectResult")).getResponse() == null) {
                files = new Files();
                files.setFileUrl((String)result.get("url"));
                files.setFileName(file.getResource().getFilename());
                files.setFileType(FileTypeUtil.getType(tempFile));
                files.setUploadTime(new Date());
                filesMapper.insert(files);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }


}
