package com.example.demo.service;

import java.lang.reflect.Type;

public interface RedisJsonService {

	void setJsonString(String key, Object object);
	
	Object getJosnObject(String key, Type type);
}
