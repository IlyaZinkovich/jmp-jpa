package com.epam.jmp.jpa.model;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "EMPLOYEE_PROJECTS",
            joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"))
    private List<Employee> employees;

    public Project() {

    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
