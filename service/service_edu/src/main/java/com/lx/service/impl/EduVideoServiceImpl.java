package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.client.VideoClient;
import com.lx.entity.EduCourse;
import com.lx.entity.EduVideo;
import com.lx.mapper.EduVideoMapper;
import com.lx.service.IEduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements IEduVideoService {

    @Autowired
    private VideoClient videoClient;

    //根据课程id删除小节 删除文件对应视频
    @Override
    public void removeVideoById(String id) {
        //根据课程id删除视频
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id); //根据课程id查询
        //查询指定的列
        queryWrapper.select("video_source_id");
        //查询出id对应的所有小节对象
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);
        List<String> list = new ArrayList<>();
        //把List<EduVideo>变成List<String>
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String courseId = eduVideo.getCourseId();
            if (!StringUtils.isEmpty(courseId)){
                list.add(courseId);
            }
        }
        //根据多个视频id删除多个视频
        if (eduVideos.size()>0){
            videoClient.removeMoreVideo(list);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
