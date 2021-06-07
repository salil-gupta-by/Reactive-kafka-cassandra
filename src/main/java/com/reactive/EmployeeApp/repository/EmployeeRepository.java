package com.reactive.EmployeeApp.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.reactive.EmployeeApp.model.Employee;

@Repository
public interface EmployeeRepository extends ReactiveCassandraRepository <Employee, Integer> {

}
