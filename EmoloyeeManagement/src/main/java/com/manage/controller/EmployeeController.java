package com.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.exception.ResourceNotFoundException;
import com.manage.model.Employee;
import com.manage.repository.EmployeeRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	// get all employee
	@GetMapping("/employees")
	public List<Employee> getAllEmployee(){
		return employeeRepo.findAll();
	}
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepo.save(employee);
	}
	
	// get employee by id api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Employee not exsist with id : " + id));
		return ResponseEntity.ok(employee);
	}
	
	// update employee api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Employee not exsist with id : " + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepo.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable long id){
		Employee employee = employeeRepo.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Employee not exsist with id : " + id));
		employeeRepo.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted successfully!", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
