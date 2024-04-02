package com.pvxdv.loggerspringaop.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("within(com.pvxdv.loggerspringaop.api..*)")
    public void applicationControllerPackage() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void applicationControllerBean() {
    }
    @Around("applicationControllerBean() && applicationControllerPackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("Request for {}.{}() with arguments[s]={}", joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        Instant start = Instant.now();
        Object returnValue = joinPoint.proceed();
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Response for {}.{} with Result ={}", joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), returnValue);

        logger.info("Time Taken =" + new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));

        return  returnValue;
    }

    @Pointcut("within(com.pvxdv.loggerspringaop..*)")
    public void applicationErrorPackage() {
    }
    @AfterThrowing(pointcut = "applicationErrorPackage()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e){
        logger.error("Exception in {}.{} with cause={} , with message={}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL", e.getMessage() !=null ? e.getMessage() : "NULL");
    }
}
