package com.cisco.shopapp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.cisco.shopapp.service.*.*(..))")
    public void logBefore(JoinPoint point) {
        logger.info("Called : " + point.getSignature());
        Object[] args = point.getArgs();
        for(Object arg: args) {
            logger.info("Argument : " + arg);
        }
    }

    @After("execution(* com.cisco.shopapp.service.*.*(..))")
    public void logAfter(JoinPoint point) {
        logger.info("**********");
    }

    @AfterThrowing(value = "execution(* com.cisco.shopapp.service.*.*(..))", throwing = "ex")
    public void logException(Exception ex) {
        logger.info(ex.getMessage());
    }

    @Around("execution(* com.cisco.shopapp.service.*.*(..))")
    public  Object doProfile(ProceedingJoinPoint pjp) throws Throwable{
        long startTime = new Date().getTime();
            Object ret = pjp.proceed(); // invoke the actual method
        long endTime = new Date().getTime();
        logger.info("Time : " + pjp.getSignature() + " ---> " + (endTime - startTime) + " ms");
        return  ret;
    }
}
