package com.lx.controller;


import com.lx.entity.EduChapter;
import com.lx.entity.chapter.OneChapter;
import com.lx.pojo.RC;
import com.lx.service.IEduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
@RestController
@RequestMapping("/eduTeacher/eduChapter")
//@CrossOrigin
@Api(value = "章节信息接口" ,description = "章节信息接口")
public class EduChapterController {

    @Autowired
    private IEduChapterService eduChapterService;

    @GetMapping("/getAllChapter/{courseId}")
    public RC getAllChapter(@PathVariable String courseId){

        List<OneChapter> list= eduChapterService.getChapter(courseId);

        return RC.success().data("getChapter",list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public RC addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return RC.success();
    }

    @GetMapping("/getChapterById/{chapterId}")
    public RC getChapterById(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);

        return RC.success().data("chapter",eduChapter);

    }
    //修改章节
    @PostMapping("/updateChapter")
    public RC updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return RC.success();
    }

    //删除章节
    @GetMapping("deleteChapter/{chapterId}")
    public RC deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag){
            return RC.success();
        }else {
            return RC.err();
        }
    }

}

