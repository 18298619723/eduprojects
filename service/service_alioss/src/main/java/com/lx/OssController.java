package com.lx;

import com.lx.pojo.RC;
import com.lx.service.IOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss")
@CrossOrigin
public class OssController {

    @Autowired
    private IOssService ossService;

    @PostMapping("uploadFile")
    public RC uploadFile(MultipartFile file){

        String url= ossService.uploadOssFile(file);
        return RC.success().data("url",url);
    }
}
