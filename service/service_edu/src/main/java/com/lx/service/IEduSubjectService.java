package com.lx.service;

import com.lx.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author
 * @since
 */
public interface IEduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file, IEduSubjectService subjectService);

    List<OneSubject> getAllSubjectSort();
}
