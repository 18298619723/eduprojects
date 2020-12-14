package com.lx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.entity.vo.TeacherPage;
import com.lx.pojo.RC;
import com.lx.entity.EduTeacher;
import com.lx.service.IEduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author
 * @since 2020-11-13
 */

@Api(value = "讲师接口" ,description = "讲师接口")
@RestController
//@CrossOrigin
@RequestMapping("/eduTeacher/teacher")
public class EduTeacherController {

    @Autowired
    private IEduTeacherService eduTeacherService;


    @GetMapping("/getAllTeacher")
    public RC getAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return RC.success().data("list",list);
    }

    @GetMapping("/deleteTeacher/{id}")
    public RC deleteTeacher(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if (b){
            return RC.success();
        }else {
            return RC.err();
        }
    }

    @GetMapping("/AllTeacherByPage/{currentPage}/{pageSize}")
    public RC AllTeacherByPage(@PathVariable int currentPage,@PathVariable int pageSize){
        //创建page分页对象
        Page<EduTeacher> teacherPage = new Page<>(currentPage,pageSize);
        //把分页所有数据封装到teacherPage对象中
        eduTeacherService.page(teacherPage,null);

        long total = teacherPage.getTotal();//数据总条数
        List<EduTeacher> list = teacherPage.getRecords();//数据list集合

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",list);

        return RC.success().data(map);
    }


    @PostMapping("/addTeacher")
    public RC addTeacher(@RequestBody EduTeacher eduTeacher){

        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return RC.success();
        }else {
           return RC.err();
        }
    }

    @GetMapping("/getTeacherByUid/{id}")
    public RC getTeacherByUid(@PathVariable String id){
        EduTeacher tid = eduTeacherService.getById(id);
        return RC.success().data("id",tid);
    }

    @PostMapping("/updateTeacher")
    public RC updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.saveOrUpdate(eduTeacher);
        if (b){
            return RC.success();
        }else {
            return RC.err();
        }
    }


    //@RequestBody(required = false) TeacherPage teacherPage该参数值可以为空
    @PostMapping("/GetTeacherByConditionPage/{currentPage}/{PageSize}")
    public RC GetTeacherByConditionPage(@PathVariable int currentPage, @PathVariable int PageSize, @RequestBody(required = false) TeacherPage teacherPage){

        Page<EduTeacher> pageTeacher = new Page<>(currentPage,PageSize);
        //构建条件wrapper对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherPage.getName();
        Integer level = teacherPage.getLevel();
        String start = teacherPage.getStart();
        String over = teacherPage.getOver();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        if (level != null ){
            wrapper.eq("level",level);
        }

        //ge:大于等于  eq:等于   ne:不等于
        if (start != null ){
            wrapper.ge("gmt_create",start);
        }
        //le:小于等于
        if (over != null ){
            wrapper.le("gmt_create",over);
        }
        eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return RC.success().data("total",total).data("records",records);
    }


}

