package util;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Department;
import model.Employee;

public class ListOperation {

    public List<Department> getDepartments(){
        List<Department> departments = Arrays.asList(
            new Department(1, "HR"),
            new Department(2, "ADMIN"),
            new Department(3, "DEVELOPER")
    );
        return departments;
    }

    public List<Employee> getListOfEmployee(){
        List<Employee> employeeist = Arrays.asList(
            new Employee(
            1, 5, getDepartments().get(0), "Nitesh", 
            "nitesh@gmail.com", "9884033195", 33000),
            new Employee(
            2, 10, getDepartments().get(1), "Mukesh", 
            "mukesh@gmail.com", "9884033196", 43000),
            new Employee(
            3, 7, getDepartments().get(0), "Raju", 
            "raju@gmail.com", "9884033197", 63000),
            new Employee(
            5, 9, getDepartments().get(2), "Rohit", 
            "rohit@gmail.com", "9884033198", 75000),
             new Employee(
            4, 8, getDepartments().get(1), "Rohit", 
            "rohit@gmail.com", "9884033198", 73000)
        );

        return employeeist;

    }

    public void displayList(List<Employee> emps){
                emps.forEach(e->{
                System.out.println(e.getId()+" "+e.getName()+" "+e.getEmail()+" "+
                e.getPhone()+" "+e.getExperienced()+" "+e.getSalary()+" "+e.getDepartment().getName());
       });
    }

    public Map<String,Long> countEmployeeBYDepartmentId(List<Employee> emps){
             return   emps
                    .stream()
                    .collect(Collectors.groupingBy(e-> e.getDepartment().getName(), Collectors.counting()));
    }

    public List<Employee> getEmployeeByDepartmentName(List<Employee> emps, String departmentName ){
        
        return emps
                .stream()
                .filter(e-> e.getDepartment().getName().equals(departmentName))
                .collect(Collectors.toList());
    }

    public List<Employee> getSortedEmployee(List<Employee> emps, String order){
        Comparator<Employee> comprator = Comparator.comparing(Employee::getSalary);
        if("desc".equalsIgnoreCase(order))
            comprator =  comprator.reversed();
        return emps
                .stream()
                .sorted(comprator)
                .toList();
    }

    public List<Employee> getEmployeeBySalary(List<Employee> emps, double salary, String operator){
        
            return emps
                    .stream()
                    .filter(e-> {
                        if("greater".equalsIgnoreCase(operator)){
                            return e.getSalary() > salary;
                        }
                        else if("less".equalsIgnoreCase(operator)){
                            return e.getSalary() < salary;
                        }
                        else if("equals".equalsIgnoreCase(operator)){
                            return e.getSalary() == salary;
                        }
                        else {
                            throw new IllegalArgumentException("Invalid operator: " + operator);
                            }
                    }).toList();
    }

}
