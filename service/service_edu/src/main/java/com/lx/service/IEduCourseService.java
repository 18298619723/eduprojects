package com.lx.service;

import com.lx.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.entity.vo.CourseInfoVo;
import com.lx.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
public interface IEduCourseService extends IService<EduCourse> {

    //添加课程信息基本方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);
    //修改课程基本信息
    void UpdateCourseInfo(CourseInfoVo courseInfoVo);
    //根据课程id擦汗寻课程确认信息
    CoursePublishVo getCourseByCourseId(String courseId);
    //删除课程
    void removeCourse(String id);
}
