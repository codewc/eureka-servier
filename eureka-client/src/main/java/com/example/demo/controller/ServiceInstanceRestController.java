package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.GenericDao;
import com.example.demo.entity.Person;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/db")
@Slf4j
public class ServiceInstanceRestController {

	@Lazy(true)
	@Autowired
	private GenericDao<Person, Long> basicDaoImpl;

	@RequestMapping("/query")
	@ResponseBody
	public List<Person> home(@RequestParam(name = "name") String name) {
		List<Person> personList = basicDaoImpl.find("from Person");
		for (Person person : personList) {
			log.info(person.getName() + person.getAge());
		}
		return personList;
	}
}
