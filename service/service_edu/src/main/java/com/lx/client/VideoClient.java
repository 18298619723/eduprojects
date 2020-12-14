package com.lx.client;

import com.lx.pojo.RC;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="server-vod",fallback = VideoClientHystrix.class)//提供者的服务名
@Component
public interface VideoClient {

    //定义调用方法的路径
    //@PathVariable一定要指定它的参数名称
    //删除阿里云上的视频
    @DeleteMapping("/eduTeacher/edVideo/removeVideo/{id}")
     RC removeVideo(@PathVariable("id") String id);

    //根据多个id删除多个视频
    //多个视频id  List<String> videoIdsList
    @DeleteMapping("/eduVideo/vod/removeMoreVideo")
     RC removeMoreVideo(@RequestParam List<String> videoIdsList);
}
