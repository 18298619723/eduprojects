package com.lx.mapper;

import com.lx.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

   CoursePublishVo getCourseVo(String courseId);


}
