package com.lx.service.impl;

import com.lx.service.IMsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MsmServiceImpl implements IMsmService {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;
}
