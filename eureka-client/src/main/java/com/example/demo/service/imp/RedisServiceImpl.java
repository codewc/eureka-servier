package com.example.demo.service.imp;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	/**
	 * stringRedisTemplate只能缓存key-value的String类型
	 */
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void set(String key, Object value) {
		 ValueOperations<String,Object> vo = redisTemplate.opsForValue();
	        vo.set(key, value);
	}

	@Override
	public <T> T get(String key) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        return (T) vo.get(key);
	}

}
