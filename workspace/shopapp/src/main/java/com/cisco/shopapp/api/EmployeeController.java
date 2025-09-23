package com.cisco.shopapp.api;

import com.cisco.shopapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    Employee employee = new Employee();
    public EmployeeController() {
        employee.setId(123);
        employee.setTitle("Sr.Prog");
        var personal = new HashMap<String, String>();
        personal.put("firstName", "Roger");
        personal.put("lastName", "Smith");
        personal.put("phone", "1234567890");

        var progSkills = new ArrayList<String>();
        progSkills.add("Java");
        progSkills.add("Spring");

        employee.setPersonal(personal);
        employee.setProgrammingSkills(progSkills);
    }

    // Java to JSON mapper.writeValueAsString(employee)
    // JSON to JsoNode mapper.readTree(json)
    // apply to apply patch to JsonNode
    // convert JsonNode to Java treeToValue()
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Employee updateEmployee(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception {
        ObjectMapper mapper = new ObjectMapper(); // used to convert Java <-->JSON

        var target = patch.apply(mapper.readTree(mapper.writeValueAsString(employee)));
        // target can be sent to database for actual update ...
        return mapper.treeToValue(target, Employee.class);
    }
}
