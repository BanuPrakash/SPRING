package com.cisco.asycexamples;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceHandler {
    @EventListener
    @Async  // don't use Tomcat Thread, use Spring container provided Thread
    public void handleNotification(PatientDischargeEvent event) {
        System.out.println("Notify Patient  " + event.getPatientId() + " by Thread : " + Thread.currentThread()  + "  -- " );
    }
}
