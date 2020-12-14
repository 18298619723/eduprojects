package com.lx.service;

import org.springframework.web.multipart.MultipartFile;

public interface IVideoService {
    String uploadVod(MultipartFile file);
}
