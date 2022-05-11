package com.sm;

import com.google.gson.Gson;
import com.sm.model.Employee;
import com.sm.response.GenericResponse;
import com.sm.response.StatusResponse;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.sm.response.StatusResponse.*;
import static spark.Spark.*;

public class EmployeeManagement {

    private static Map<Long, Employee> employeeMap = new HashMap<Long, Employee>();
    static{
        employeeMap.put(1L,new Employee("Ali","IT"));
        employeeMap.put(2L,new Employee("Ahmed","Finance"));
        employeeMap.put(3L,new Employee("Hunain","Operation"));
        employeeMap.put(4L,new Employee("Awais","IT"));
        employeeMap.put(5L,new Employee("Mubashir","IT"));
    }

    public static void main(String[] args) {
        final Random random = new Random();

        // Creates a new Employee resource
        post("/employees", (request, response) -> {
            Employee reqEmployee= new Gson().fromJson(request.body(), Employee.class);
            Employee employee = new Employee(reqEmployee.getName(), reqEmployee.getDepartment());
            Long id = random.nextLong();
            employeeMap.put(id, employee);
            response.status(201);
            return new Gson()
                    .toJson(new GenericResponse(SUCCESS));
        });

        // Gets the Employee resource for the provided id
        get("/employees/:id", (request, response) -> {
            Employee employee = employeeMap.get(Long.valueOf(request.params(":id")));
            if (employee != null) {
                return "Name: " + employee.getName() + ", Department: " + employee.getDepartment();
            }
            return getErrorStatus(response);
        });

        // x-www-urlencoded values e.g. name=Hello&department=IT
        // you get them by using request.queryParams("name")
        put("/employees/:id", (request, response) -> {
            Long id = Long.valueOf(request.params(":id"));
            Employee employee = employeeMap.get(id);
            if (employee != null) {
                String name = request.queryParams("name");
                String department = request.queryParams("department");
                if (name != null) {
                    employee.setName(name);
                }
                if (department != null) {
                    employee.setDepartment(department);
                }

                return new Gson()
                        .toJson(new GenericResponse(SUCCESS,"Employee with id '" + id + "' updated"));
            }
            return getErrorStatus(response);

        });

        // Deletes the Employee resource for the provided id
        delete("/employee/:id", (request, response) -> {
            Long id = Long.valueOf(request.params(":id"));
            Employee employee = employeeMap.remove(id);
            if (employee != null) {
                return new Gson()
                        .toJson(new GenericResponse(SUCCESS,"Employee with id '" + id + "' deleted"));
            }
            return getErrorStatus(response);
        });

        // Gets all employee ids
        get("/employees", (request, response) -> {
            String ids = "";
            for (Long id : employeeMap.keySet()) {
                ids += id + " ";
            }
            return new Gson()
                    .toJson(new GenericResponse(SUCCESS,ids));
        });
    }

    private static Object getErrorStatus(Response response) {
        response.status(404);
        return new Gson()
                .toJson(new GenericResponse(ERROR, "Employee not found"));
    }
}