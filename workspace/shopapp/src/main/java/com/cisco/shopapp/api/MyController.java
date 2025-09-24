package com.cisco.shopapp.api;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final Counter myCounter;
    private final Timer timer;

    public MyController(MeterRegistry registry) {
        myCounter = Counter.builder("my.counter")
                .description("Counts MyController Count")
                .register(registry);
        timer = Timer.builder("mycontroller.timer")
                .description("Times Mycontroller")
                .register(registry);
    }

    @GetMapping("api/increment")
    public String sayHello() throws Exception {
        myCounter.increment();
//        return "Hello";
        return timer.recordCallable(() -> greetHello());
//        return timer.recordCallable(() -> "Hello");
    }

    private  String greetHello() {
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return  "Hello!!!";
    }
}
