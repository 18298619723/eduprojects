package com.lx.client;

import com.lx.pojo.RC;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoClientHystrix implements VideoClient{
    @Override
    public RC removeVideo(String id) {
        return RC.success().msg("删除视频出错......");
    }

    @Override
    public RC removeMoreVideo(List<String> videoIdsList) {
        return RC.success().msg("删除多个视频出错......");
    }
}
