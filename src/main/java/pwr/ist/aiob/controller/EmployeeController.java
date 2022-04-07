package pwr.ist.aiob.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.ist.aiob.model.EmployeeDAO;
import pwr.ist.aiob.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost")
class EmployeeController {

    private final EmployeeRepository employeeRepository;

    EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    ResponseEntity<List<EmployeeDAO>> all() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.findAll());
    }

    @PostMapping("/employees")
    ResponseEntity<EmployeeDAO> newEmployee(@RequestBody EmployeeDAO newEmployee) {
        employeeRepository.save(newEmployee);
        return ResponseEntity.status(HttpStatus.OK).body(newEmployee);
    }

    @GetMapping("/employees/{id}")
    ResponseEntity<EmployeeDAO> one(@PathVariable Long id) {
        Optional<EmployeeDAO> employee = employeeRepository.findById(id);

        if (employee.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<EmployeeDAO> updateEmployee(@RequestBody EmployeeDAO newEmployee, @PathVariable Long id) {
        Optional<EmployeeDAO> employee = employeeRepository.findById(id);

        if (employee.isPresent()){
            employeeRepository.save(newEmployee);
            return ResponseEntity.status(HttpStatus.OK).body(newEmployee);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity deleteEmployee(@PathVariable Long id) {
        Optional<EmployeeDAO> employee = employeeRepository.findById(id);

        if (employee.isPresent()){
            employeeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
