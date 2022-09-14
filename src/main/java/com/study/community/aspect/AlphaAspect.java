package com.study.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
public class AlphaAspect {

    @Pointcut("execution(* com.study.community.service.*.*(..))")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void before(){
        System.out.println("before");
    }

    @After("pointCut()")
    public void after(){
        System.out.println("After");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("AfterReturning");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("AfterThrowing");
    }
    @Around("pointCut()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("aroundBefore");
        Object proceed = joinPoint.proceed();
        System.out.println("aroundAfter");
        return proceed;
    }


}
