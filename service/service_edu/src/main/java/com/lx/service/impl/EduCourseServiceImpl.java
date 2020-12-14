package com.lx.service.impl;

import com.lx.config.LXException;
import com.lx.entity.EduCourse;
import com.lx.entity.EduCourseDescription;
import com.lx.entity.vo.CourseInfoVo;
import com.lx.entity.vo.CoursePublishVo;
import com.lx.mapper.EduCourseMapper;
import com.lx.service.IEduChapterService;
import com.lx.service.IEduCourseDescriptionService;
import com.lx.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.service.IEduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {


    //注入课程描述
    @Autowired
    private IEduCourseDescriptionService eduCourseDescriptionService;
    //注入小节
    @Autowired
    private IEduVideoService eduVideoService;
    //注入章节
   @Autowired
   private IEduChapterService eduChapterService;

   @Cacheable(value = "banner",key = "'saveCourseInfo'")
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中添加数据
        EduCourse eduCourse = new EduCourse();
        //将eduCourse对象的值设置为courseInfoVo实体对象中的值
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.insert(eduCourse);
        //判断课程添加是否成功
        if (i==0){
            throw new LXException(20001,"课程信息添加失败");
        }
        //获取eduCourse的id
        String cid=eduCourse.getId();
        System.out.println(cid);

        //向课程描述表中添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //将eduCourseDescription对象的id设置为eduCourse的id,因为课程和课程简介是一对一的关系
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //根据id查询发布课程确认信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        System.out.println(eduCourse);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询描述表
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);

        courseInfoVo.setDescription(description.getDescription());
        System.out.println(courseInfoVo);

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void UpdateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        System.out.println(i);
        if (i==0){
            throw new LXException(20001,"修改失败");
        }
        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    //根据id得到课程最终确认信息
    @Override
    public CoursePublishVo getCourseByCourseId(String id) {
        CoursePublishVo courseVo = baseMapper.getCourseVo(id);
        return courseVo;

    }

    //删除课程
    @Override
    public void removeCourse(String id) {
        //根据课程id删除小节
        eduVideoService.removeVideoById(id);
        //根据课程id删除章节
        eduChapterService.removeChapterById(id);

        //课程跟课程描述一一对应
        //根据课程id删除课程描述
        eduCourseDescriptionService.removeById(id);
        //根据课程id删除课程本身
        int i = baseMapper.deleteById(id);
        if (i==0){
            throw new LXException(20001,"删除失败");
        }


    }

}

