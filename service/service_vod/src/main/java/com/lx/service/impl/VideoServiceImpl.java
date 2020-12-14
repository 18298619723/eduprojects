package com.lx.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.lx.service.IVideoService;
import com.lx.utils.VideoUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@Service
public class VideoServiceImpl implements IVideoService {
    @Override
    public String uploadVod(MultipartFile file) {

        //01.02.03.mp4
        String fileName = file.getOriginalFilename();//文件的原始名称
        //不带后缀名，截取后面后缀
        //01.02.03
        String title = fileName.substring(0, fileName.lastIndexOf("."));

        try {
            InputStream inputStream = file.getInputStream();//上传文件输入流
            UploadStreamRequest request = new UploadStreamRequest(VideoUtils.KEY_ID, VideoUtils.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
               return response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

}
