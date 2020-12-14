package com.lx.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lx.config.LXException;
import com.lx.pojo.RC;
import com.lx.service.IVideoService;
import com.lx.utils.InitVodClient;
import com.lx.utils.VideoUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduVideo/vod")
@CrossOrigin
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @PostMapping("uploadVideo")
    @ApiModelProperty(value = "上传视频接口")
    public RC uploadVideo(MultipartFile file){

        String id=videoService.uploadVod(file);

        return RC.success().data("VideoId",id);
    }

    //根据id删除视频
    @DeleteMapping("removeVideo/{id}")
    public RC removeVideo(@PathVariable String id){
        DefaultAcsClient client = InitVodClient.initVodClient(VideoUtils.KEY_ID, VideoUtils.KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);
        try {
            client.getAcsResponse(request);
            return RC.success();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new LXException(20001,"删除失败");
        }
    }

    //根据多个id删除多个视频
    @DeleteMapping("removeMoreVideo")
    public RC removeMoreVideo(@RequestParam List<String> videoIdsList){
        //初始化client
        DefaultAcsClient client = InitVodClient.initVodClient(VideoUtils.KEY_ID, VideoUtils.KEY_SECRET);
        //创建删除对象request
        DeleteVideoRequest request = new DeleteVideoRequest();
        //遍历videoIdsList获取多个id  StringUtils为org.apache.commons.lang.StringUtils
        String videoIds = StringUtils.join(videoIdsList.toArray(), ",");
        System.out.println(videoIds);
        request.setVideoIds(videoIds);
        try {
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return RC.success();
    }
}
