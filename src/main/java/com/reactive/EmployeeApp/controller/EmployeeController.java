package com.reactive.EmployeeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.reactive.EmployeeApp.model.EmployeeRequest;
import com.reactive.EmployeeApp.model.EmployeeResponse;
import com.reactive.EmployeeApp.model.SkillSetRequest;
import com.reactive.EmployeeApp.model.SkillSetResponse;
import com.reactive.EmployeeApp.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	
	
	@PostMapping("/createEmployee")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<EmployeeResponse> createEmployee (@RequestBody EmployeeRequest employee){
		
		log.debug("inside createEmployee controller");
		
		return employeeService.save(employee);
	}
	
	@PostMapping("/findEmpSkillset")
	public Flux<SkillSetResponse> findEmpBySkillSet(@RequestBody SkillSetRequest skillrequest){
		
		log.debug("inside findEmpBySkillSet controller");
		
		return employeeService.findEmpBySkills(skillrequest.getJava_exp(),skillrequest.getSpring_exp());
	}

}
