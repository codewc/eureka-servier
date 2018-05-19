package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.demo.dao.GenericDao;
import com.example.demo.entity.Person;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonService {

	@Lazy(true)
	@Autowired
	private GenericDao<Person, Long> basicDaoImpl;
	
	public List<Person> find(String name) {
		List<Person> personList = basicDaoImpl.find("from Person");
		for (Person person : personList) {
			log.info(person.getName() + person.getAge());
		}
		return personList;
	}
}
