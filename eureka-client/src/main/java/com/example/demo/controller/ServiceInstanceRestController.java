package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import com.example.demo.service.RedisService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/db")
public class ServiceInstanceRestController {

	@Autowired
	PersonService personService;
	
	@Autowired
	RedisService redisServiceImpl;
	
	@RequestMapping("/query")
	@ResponseBody
	public List<Person> home(@RequestParam(name = "name") String name) {
		List<Person> person = new ArrayList<Person>();
		String value =  (String) redisServiceImpl.get(name);
		if(StringUtils.isBlank(value)) {
			person = personService.find(name);
			redisServiceImpl.set(name, person.toString());
			log.info("这是查询结果");
		}else {
			person.add(Person.builder().name(value).build());
		}
		return person;
	}
}
