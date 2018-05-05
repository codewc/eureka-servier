package com.example.demo.service;

/**
 * Redis缓存
 * 
 * @author wangchun
 *
 */
public interface RedisService {
	void set(String key, Object value);

	<T> T get(String key);
}
