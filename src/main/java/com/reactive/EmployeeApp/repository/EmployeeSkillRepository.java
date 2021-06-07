package com.reactive.EmployeeApp.repository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.reactive.EmployeeApp.model.EmployeeSkill;

import reactor.core.publisher.Flux;

@Repository
public interface EmployeeSkillRepository extends ReactiveCassandraRepository <EmployeeSkill, EmployeeSkill> {
	
	@Query("SELECT * FROM emp_skill where java_exp >=?0 and spring_exp >=?1 ALLOW FILTERING")
	Flux<EmployeeSkill> findBySkillSet (double java_exp, double spring_exp);

}
