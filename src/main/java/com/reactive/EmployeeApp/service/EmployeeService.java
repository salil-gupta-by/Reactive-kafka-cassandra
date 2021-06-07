package com.reactive.EmployeeApp.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.EmployeeApp.controller.EmployeeController;
import com.reactive.EmployeeApp.model.Employee;
import com.reactive.EmployeeApp.model.EmployeeRequest;
import com.reactive.EmployeeApp.model.EmployeeResponse;
import com.reactive.EmployeeApp.model.EmployeeSkill;
import com.reactive.EmployeeApp.repository.EmployeeRepository;
import com.reactive.EmployeeApp.repository.EmployeeSkillRepository;
import com.reactive.EmployeeApp.util.KafkaProducerEmp;
import com.reactive.EmployeeApp.model.SkillSetResponse;


import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.core.publisher.Flux;

@Service
public class EmployeeService {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeSkillRepository employeeSkillRepository;
	
	@Autowired
	KafkaProducerEmp kafkaProducerEmp;
	
	
	public Mono<EmployeeResponse> save (EmployeeRequest employee){
		
		log.debug("inside Employee save" + employee.getEmp_id());
		
		String status="";
		Employee emp= new Employee(employee.getEmp_id(),employee.getEmp_name(),employee.getEmp_city(),employee.getEmp_phone());
		EmployeeSkill emp_skill= new EmployeeSkill(employee.getEmp_id(),employee.getJava_exp(),employee.getSpring_exp());
		
		kafkaProducerEmp.sendMessages(employee);

		
		return employeeRepository.existsById(emp.getEmp_id())
		.flatMap(exists -> {
			if (exists) {
				log.debug("Employee Already exists");
				return Mono.zip(Mono.just(emp),
						Mono.just(emp_skill))
						.map(t -> new EmployeeResponse (t.getT1().getEmp_id(),t.getT1().getEmp_name(), t.getT1().getEmp_city(), t.getT1().getEmp_phone(),t.getT2().getJava_exp(),t.getT2().getSpring_exp(),"Already Exists"));		

				
			}else
			{
				log.debug("Employee Create new");
				return Mono.zip(Mono.just(emp)
						.flatMap(employeeRepository::save)				
						.log("Employee Object "),
						Mono.just(emp_skill)
						.flatMap(employeeSkillRepository::save)				
						.log("Employee Skill Object "))
						.map(t -> new EmployeeResponse (t.getT1().getEmp_id(),t.getT1().getEmp_name(), t.getT1().getEmp_city(), t.getT1().getEmp_phone(),t.getT2().getJava_exp(),t.getT2().getSpring_exp(),"Created"));		
				
			}
			
		});
		
		
				
	}
	
	
	
	
	public Flux<SkillSetResponse> findEmpBySkills (double java_exp, double spring_exp){
		
		log.debug("inside the findEmpBySkills service "+java_exp+ " " +spring_exp );
		
		
		Flux<EmployeeSkill> flux1 = employeeSkillRepository.findBySkillSet(java_exp,spring_exp).sort((f1,f2) -> Integer.valueOf(f1.getEmp_id()).compareTo(Integer.valueOf(f2.getEmp_id())));

		Flux<Employee> flux2 = employeeRepository.findAll().sort((f1,f2) -> Integer.valueOf(f1.getEmp_id()).compareTo(Integer.valueOf(f2.getEmp_id())));

		
		return flux1.zipWith(flux2, (a,b) -> new SkillSetResponse (a.getEmp_id(),b.getEmp_name(),b.getEmp_city(),b.getEmp_phone(),a.getJava_exp(),a.getSpring_exp()));
				
										
		
	}
	

}
