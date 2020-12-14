package com.lx.controller;

import com.lx.entity.subject.OneSubject;
import com.lx.pojo.RC;
import com.lx.service.IEduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author
 * @since 2020-11-18
 */

@Api(value = "课程分类接口" ,description = "课程分类接口")
@RestController
@RequestMapping("/eduTeacher/subject")
//@CrossOrigin
public class EduSubjectController {
    @Autowired
    private IEduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    @ApiModelProperty(value = "添加课程分类接口",name = "添加课程分类接口")
    //获取需要上传的file文件
    public RC saveSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return RC.success();
    }

    @GetMapping("getAllSubject")
    @ApiModelProperty(value = "查询课程分类接口",name = "查询课程分类接口")
    public RC getAllSubject(){
        List<OneSubject> list=eduSubjectService.getAllSubjectSort();
        return RC.success().data("list",list);
    }
}

