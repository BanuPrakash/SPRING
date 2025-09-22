package com.cisco.demo;

import com.cisco.demo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

//        String[] names = ctx.getBeanDefinitionNames(); // all beans within container
//        for(String bean: names) {
//            System.out.println(bean);
//        }

        System.out.println("Pull the bean from Container");
        AppService service = ctx.getBean("appService", AppService.class);
        service.register();
    }

}
