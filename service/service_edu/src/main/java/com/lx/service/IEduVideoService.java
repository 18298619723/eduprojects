package com.lx.service;

import com.lx.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lx
 * @since 2020-11-19
 */
public interface IEduVideoService extends IService<EduVideo> {

    void removeVideoById(String id);
}
