package com.lx.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.lx.service.IOssService;
import com.lx.utils.ConstantUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements IOssService {

    @Override
    public String uploadOssFile(MultipartFile file) {

        String endpoint = ConstantUtil.END_POINT;
        String accessKeyId = ConstantUtil.KEY_ID;
        String accessKeySecret = ConstantUtil.KEY_SECRET;
        String bucketName=ConstantUtil.BUCK_NAME;

        try{
            //创建OSS对象
            OSS ossClient=new OSSClient(endpoint,accessKeyId,accessKeySecret);
            //获取文件上传输入流
            InputStream inputStream =file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();

            /**
             * 防止多次上传同一文件，之前的文件被覆盖
             */
            //根据随机数获取不同的文件名
            //dadfeaa03.jpg
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid+filename;

            //根据日期分类文件
            //2020-11-11/dadfeaa.jpg
            String date = new DateTime().toString("yyyy-MM-dd");//生成文件夹
            filename =date+filename;

            /**
             * 调用oss方法实现上传
             * bucketName:Bucket名称
             * filename:上传到oss文件的路径和文件名称
             * inputStream:文件上传输入流
             */
            ossClient.putObject(bucketName,filename,inputStream);

            ossClient.shutdown();

            // https://wg1.oss-cn-beijing.aliyuncs.com/BYC3.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;

            return url;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
