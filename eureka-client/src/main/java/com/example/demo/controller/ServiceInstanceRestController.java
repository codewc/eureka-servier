package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.param.FM1001Request;
import com.example.demo.service.PersonService;
import com.example.demo.service.RedisJsonService;
import com.example.demo.service.RedisService;
import com.example.demo.util.BeanValidator;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@Slf4j
@RequestMapping("/db")
public class ServiceInstanceRestController {

	@Autowired
	PersonService personService;

	@Autowired
	RedisService redisServiceImpl;

	@Autowired
	RedisJsonService redisJsonServiceImp;

	@RequestMapping("/query")
	@ResponseBody
	public List<Person> home(FM1001Request request) {
		BeanValidator.check(request);
		String name = request.getName();
		List<Person> person = new ArrayList<Person>();
		List<Person> persons = redisJsonServiceImp.getJosnObject(name, new TypeToken<List<Person>>(){}.getType());
		if (persons == null || persons.size() == 0) {
			person = personService.find(name);
			redisJsonServiceImp.setJsonString(name, person);
		} else {
			person = persons;
		}
		return person;
	}
}
