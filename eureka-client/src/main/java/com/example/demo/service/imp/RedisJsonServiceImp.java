package com.example.demo.service.imp;

import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.demo.service.RedisJsonService;
import com.google.gson.Gson;

@Service
public class RedisJsonServiceImp implements RedisJsonService{

	/**
	 * stringRedisTemplate只能缓存key-value的String类型
	 */
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void setJsonString(String key, Object object) {
		ValueOperations<String,Object> vo = redisTemplate.opsForValue();
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		vo.set(key, jsonString);
	}

	@Override
	public Object getJosnObject(String key,Type type) {
		ValueOperations<String,Object> vo = redisTemplate.opsForValue();
		String jsonString = (String) vo.get(key);
		Gson gson = new Gson();
		Object obj = gson.fromJson(jsonString, type);
		return obj;
	}

}
