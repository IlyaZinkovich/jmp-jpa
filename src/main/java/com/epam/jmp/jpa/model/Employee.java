package com.epam.jmp.jpa.model;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "employee")
    private PersonalInfo personalInfo;

    @ManyToMany(mappedBy = "employees", cascade = CascadeType.PERSIST)
    private List<Project> projects;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UNIT_ID", referencedColumnName = "ID")
    private Unit unit;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }
}

/*
Create models: Employee, EmployeeStatus, Address, Personal, Project, Unit

Take into account the following:

EmployeeStatus should be Enum type
Address should be embedded to Employee object
Relationship between Employee and EmployeePersonalInfo should be one-to-one
Relationship between Employee and Project should be many-to-many
Relationship between Unit and Employee should be one-to-many
Implement Service API which provides:

Create Employee / Unit / Project
Find employee / Unit / Project by id
Delete employee / Unit / Project by id
Update Employee / Unit / Project by id
Add Employee to Unit by id’s
Assign Employee for Project by id’s
 */