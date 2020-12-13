package com.ozan.exchange.aspect;

import com.ozan.exchange.annotation.Logged;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExChangeAspect
{

    private static final Logger logger = LoggerFactory.getLogger(ExChangeAspect.class);

    @Pointcut( "execution(* com.ozan.exchange.controller..*.*(..))" )
    public void inWebLayer()
    {
    }

    @Pointcut( "@annotation(logged)" )
    public void logging( Logged logged )
    {
    }

    @Around( "inWebLayer() && logging(logged)" )
    public Object around( ProceedingJoinPoint joinPoint, Logged logged ) throws Throwable
    {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
