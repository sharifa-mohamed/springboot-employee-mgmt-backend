package com.sm.controller;

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

import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Employee;
import com.sm.repository.EmployeeRepository;

@CrossOrigin//(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee emp) {
		return employeeRepository.save(emp);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));
		return ResponseEntity.ok(emp);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee emp) {
		Employee foundEmp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));

		foundEmp.setFirstName(emp.getFirstName());
		foundEmp.setLastName(emp.getLastName());
		foundEmp.setEmail(emp.getEmail());
		Employee updated = employeeRepository.save(foundEmp);

		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee foundEmp = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));
		employeeRepository.delete(foundEmp);
		Map<String, Boolean> response = new HashMap<>();
		
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
