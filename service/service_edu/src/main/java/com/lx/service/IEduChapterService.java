package com.lx.service;

import com.lx.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.entity.chapter.OneChapter;
import com.lx.pojo.RC;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
public interface IEduChapterService extends IService<EduChapter> {

    List<OneChapter> getChapter(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterById(String id);
}
