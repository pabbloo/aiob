package pwr.ist.aiob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pwr.ist.aiob.model.EmployeeDAO;


public interface EmployeeRepository extends JpaRepository<EmployeeDAO, Long> {

}
