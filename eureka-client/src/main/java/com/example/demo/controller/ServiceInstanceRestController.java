package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;

@RestController
@RequestMapping("/db")
public class ServiceInstanceRestController {

	@Resource
	PersonService personService;
	
	@RequestMapping("/query")
	@ResponseBody
	public List<Person> home(@RequestParam(name = "name") String name) {
		return personService.find(name);
	}
}
