package com.cisco.asycexamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/discharge")
public class HospitalController {
    @Autowired
    ApplicationEventPublisher publisher;

    // GET http://localhost:8080/api/discharge?patientId=511
    @GetMapping()
    public String handleDischarge(@RequestParam("id") String patientId) {
        // publish Event
        publisher.publishEvent(new PatientDischargeEvent(this, patientId));

        return "Patient Discharged!!!";
    }
}
