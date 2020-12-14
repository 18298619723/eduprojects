package com.lx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.entity.EduSubject;
import com.lx.entity.excel.SubjectData;
import com.lx.entity.subject.OneSubject;
import com.lx.entity.subject.TwoSubject;
import com.lx.listener.SubjectExcelListener;
import com.lx.mapper.EduSubjectMapper;
import com.lx.service.IEduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author
 * @since 2020-11-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,IEduSubjectService eduSubjectService) {
        try {
            InputStream in=file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Cacheable(value = "banner",key = "'getAllSubjectSort'")//在redis中缓存数据，第二次查询直接从redis中查询数据，不在去数据库查
    @Override
    public List<OneSubject> getAllSubjectSort() {
        //查询一级课程分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        //调用Mapper中的selectList方法得到一级课程集合
        List<EduSubject> eduSubjects1 = baseMapper.selectList(wrapperOne);
        //查询二级课程分类(ne:不等于)
        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> eduSubjects2 = baseMapper.selectList(wrapperTwo);

        //创建一级课程分类集合
        List<OneSubject> oneSubjectList= new ArrayList<>();
        //遍历查询出来的一级对象集合
        for (EduSubject subject1 : eduSubjects1) {
            OneSubject oneSubject = new OneSubject();
//        oneSubject.setId(eduSubject.getId());
//        oneSubject.setTitle(eduSubject.getTitle());
            //将一级对象集合中的值设置给oneSubject
            BeanUtils.copyProperties(subject1,oneSubject);
            //将oneSubject实体对象添加在oneSubjectList集合中
            oneSubjectList.add(oneSubject);
            //创建二级课程分类集合
            List<TwoSubject> twoSubjectList=new ArrayList<>();
            //在一级课程分类集合中遍历二级课程分类
            for (EduSubject subject2 : eduSubjects2) {
                //判断二级分类的parent_id和一级分类的id是否相等
                if (subject2.getParentId().equalsIgnoreCase(subject1.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject2,twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            //将二级课程分类的集合对象设置为一级对象的子集合
            oneSubject.setChildren(twoSubjectList);
        }
        return oneSubjectList;

    }

}
