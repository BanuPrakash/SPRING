package com.cisco.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    int id;
    String title;
    Map<String,String> personal = new HashMap<>();
    List<String> programmingSkills = new ArrayList<>();
}

/*
    {
        "id": 24,
        "title": "Sr.Developer",
        "personal": {
            "firstName": "Roger",
            "lastName": "Smith",
            "phone": "1234567890"
        },
        "programmingSkills": ["Java", "Spring Boot"]
    }

    Payload if JSON-PATCH:
    [
        { "op": "replace", "path": "/title", "value": "Team Lead" },
        { "op": "add", "path": "/programmingSkills/1", "value": "AWS" },
        { "op": "remove", "path": "/personal/phone" },
        {"op": "add", "path": "/personal/email", "value": "roger@cisco.com"}
    ]

    PUT Payload:
    {
        "id": 24,
        "title": "Team Lead",
        "personal": {
            "firstName": "Roger",
            "lastName": "Smith",
            "email": "roger@cisco.com"
        },
        "programmingSkills": ["Java", "AWS", "Spring Boot"]
    }
 */