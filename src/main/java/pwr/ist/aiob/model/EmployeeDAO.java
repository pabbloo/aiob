package pwr.ist.aiob.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class EmployeeDAO {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String surname;
    private String personalNumber;
    private Integer salary;
    private String jobPosition;
    private String division;
    private Date employmentDate;
    private Date firedDate;


    EmployeeDAO() {}

    public EmployeeDAO(String name, String surname, String personalNumber, Integer salary, String jobPosition, String division) {
        this.name = name;
        this.surname = surname;
        this.personalNumber = personalNumber;
        this.salary = salary;
        this.jobPosition = jobPosition;
        this.division = division;
        this.employmentDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Date getFiredDate() {
        return firedDate;
    }

    public void setFiredDate(Date firedDate) {
        this.firedDate = firedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDAO employee = (EmployeeDAO) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(personalNumber, employee.personalNumber) && Objects.equals(salary, employee.salary) && Objects.equals(jobPosition, employee.jobPosition) && Objects.equals(division, employee.division) && Objects.equals(employmentDate, employee.employmentDate) && Objects.equals(firedDate, employee.firedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, personalNumber, salary, jobPosition, division, employmentDate, firedDate);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", salary=" + salary +
                ", jobPosition='" + jobPosition + '\'' +
                ", division='" + division + '\'' +
                ", employmentDate=" + employmentDate +
                ", firedDate=" + firedDate +
                '}';
    }
}
