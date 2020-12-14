package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.config.LXException;
import com.lx.entity.EduChapter;
import com.lx.entity.EduVideo;
import com.lx.entity.chapter.OneChapter;
import com.lx.entity.chapter.TwoChapter;
import com.lx.mapper.EduChapterMapper;
import com.lx.service.IEduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.service.IEduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {

    @Autowired
    private IEduVideoService eduVideoService;


    @Override
    public List<OneChapter> getChapter(String courseId) {
        //根据课程id查询课程章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        //根据课程id查询课程小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideo = eduVideoService.list(wrapperVideo);


        List<OneChapter> FinalList = new ArrayList<>();

        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(eduChapter,oneChapter);
            FinalList.add(oneChapter);

            List<TwoChapter> list = new ArrayList<>();
            for (int j = 0; j < eduVideo.size(); j++) {
                EduVideo eduVideo1 = eduVideo.get(j);
                if (eduChapter.getId().equals(eduVideo1.getChapterId())){
                    TwoChapter twoChapter = new TwoChapter();
                    BeanUtils.copyProperties(eduVideo1,twoChapter);
                    list.add(twoChapter);
                }
            }
            oneChapter.setChildren(list);
        }

        return FinalList;
    }



    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {

        //根据chapterId章节id查询小节表,如果查询出数据，不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        //判断是否查询出数据
        if (count>0){
            throw new LXException(20001,"拥有数据，不能删除");
        }else {
            //没有查询出数据，删除章节
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterById(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
