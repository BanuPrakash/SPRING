package com.cisco.asycexamples;

import org.springframework.context.ApplicationEvent;

public class PatientDischargeEvent extends ApplicationEvent {
    private String patientId;
    public PatientDischargeEvent(Object source, String patientId) {
        super(source);
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
