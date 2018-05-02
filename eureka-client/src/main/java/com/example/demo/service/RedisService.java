package com.example.demo.service;

/**
 * Redis缓存
 * 
 * @author wangchun
 *
 */
public interface RedisService {
	void set(String key, Object value);

	Object get(String key);
}
