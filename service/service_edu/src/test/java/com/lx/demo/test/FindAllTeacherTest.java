package com.lx.demo.test;

import com.lx.service.IEduTeacherService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class FindAllTeacherTest {

    @Autowired
    private IEduTeacherService eduTeacherService;
    @Test
    public void list(){
        System.out.println(eduTeacherService.list(null));
    }
}
