package com.cisco.asycexamples;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BillingServiceHandler {

    @EventListener
    @Async  // don't use Tomcat Thread, use Spring container provided Thread
    public void processBill(PatientDischargeEvent event) {
        System.out.println("Bill Processed for " + event.getPatientId() + " by Thread " + Thread.currentThread() );
    }
}
