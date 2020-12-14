package com.lx.controller;


import com.lx.client.VideoClient;
import com.lx.config.LXException;
import com.lx.entity.EduVideo;
import com.lx.pojo.RC;
import com.lx.service.IEduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@RestController
@RequestMapping("/eduTeacher/edVideo")
//@CrossOrigin
@Api(value = "课程章节的小节接口",description = "课程章节的小节接口")
public class EduVideoController {

    @Autowired
    private IEduVideoService eduVideoService;

    @Autowired
    private VideoClient videoClient;

    //添加小节
    @PostMapping ("addVideo")
    public RC addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return RC.success();
    }

    //删除小节视频
    @GetMapping("deleteVideo/{id}")
    public RC deleteVideo(@PathVariable String id){ //该id为小节的id
        EduVideo eduVideo = new EduVideo();
         String videoSourceId = eduVideo.getVideoSourceId(); //根据小节获取到视频id
        if (!StringUtils.isEmpty(videoSourceId)){
            RC result = videoClient.removeVideo(videoSourceId);//调用提供者的删除方法
            if (result.getCode()==20001){
                throw new LXException(20001,"删除失败");
            }
        }
        System.out.println(1/0);
        eduVideoService.removeById(id);
        return RC.success();
    }

}

