package com.lx.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOssService {
    String uploadOssFile(MultipartFile file);
}
