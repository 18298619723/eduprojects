package com.lx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.config.LXException;
import com.lx.entity.EduCourse;
import com.lx.entity.vo.CourseInfoVo;
import com.lx.entity.vo.CoursePublishVo;
import com.lx.pojo.RC;
import com.lx.service.IEduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@Api(value = "课程信息接口" ,description = "课程信息接口")
@RestController
@RequestMapping("/eduTeacher/eduCourse")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private IEduCourseService eduCourseService;

    //添加课程信息
    @PostMapping("addCourseInfo")
    public RC addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        //返回添加课程信息后的id,为了添加课程大纲
        return RC.success().data("courseId",id);
    }

    //根据课程id得到课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public RC getCourseInfo(@PathVariable String courseId){

        CourseInfoVo courseInfoVo= eduCourseService.getCourseInfo(courseId);

        return RC.success().data("CourseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourse")
    public RC updateCourse(@RequestBody CourseInfoVo courseInfoVo){

        eduCourseService.UpdateCourseInfo(courseInfoVo);

        return RC.success();
    }

    //根据课程id查询发布的课程信息
    @GetMapping("getCourseByCourseId/{courseId}")
    public RC getCourseByCourseId(@PathVariable String courseId){

        CoursePublishVo coursePublishVo=eduCourseService.getCourseByCourseId(courseId);
        System.out.println(coursePublishVo);
        return RC.success().data("coursePublishVo",coursePublishVo);
    }

    //根据id发布课程
    @PostMapping("publicCourse/{id}")
    public RC publicCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return RC.success();
    }

    //查询所有课程
    @GetMapping("getAllList")
    public RC getAllList(){
        List<EduCourse> list = eduCourseService.list(null);
        System.out.println(list);
            return RC.success().data("CourseInfo",list);
    }

    //分页查询课程
    @GetMapping("getCourseByPage/{cp}/{pz}")
    public RC getCourseByPage(@PathVariable int cp ,@PathVariable int pz){
        Page<EduCourse> coursePage= new Page<>(cp,pz);
        eduCourseService.page(coursePage,null);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("records",records);

        return RC.success().data(map);

    }

    //根据id删除课程
    @GetMapping("deleteCourse/{id}")
    public RC deleteCourse(@PathVariable String id){
        eduCourseService.removeCourse(id);
        return RC.success();
    }

    //根据id查询课程
    @GetMapping("getCourseById/{id}")
    public RC getCourseById(@PathVariable String id){

        EduCourse eduCourse = eduCourseService.getById(id);
        return RC.success().data("eduCourse",eduCourse);
    }

    //修改课程信息
    @PostMapping("updateEduCourse")
    public RC updateCourse(@RequestBody EduCourse eduCourse){
        boolean b = eduCourseService.updateById(eduCourse);
        if(b){
            return RC.success();
        }else {
            throw new LXException(20001,"修改失败");
        }
    }
    
}

