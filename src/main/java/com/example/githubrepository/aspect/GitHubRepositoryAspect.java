package com.example.githubrepository.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GitHubRepositoryAspect {
    @Pointcut("execution(* com.example.githubrepository.service..*(..))")
    public void serviceMethods() {}

    @AfterReturning(pointcut = "serviceMethods()", returning = "returnValue")
    public void afterReturningServiceMethods(Object returnValue) {
        System.out.println("Return value was: " + returnValue);
    }

    @Before("serviceMethods()")
    public void beforeServiceMethods() {
        System.out.println("przed wykonaniem metody z serwisu");
    }

    @Around("serviceMethods()")
    public Object aroundServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long finishTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to proceed method " + joinPoint.getSignature().getName() + " wynosił " + finishTime + " ms");
        return proceed;
    }

    @Pointcut("execution(* com.example.githubrepository.controller..get*(..)) || " +
            "execution(* com.example.githubrepository.controller..find*(..))")
    public void controllerGetFindMethods() {}

    @Before("controllerGetFindMethods()")
    public void beforeControllerGetFindMethods() {
        System.out.println("jestem metodą GET/FIND");
    }

    @Pointcut("execution(* com.example.githubrepository.client..*(..))")
    public void clientMethods() {}

    @Around("clientMethods()")
    public Object aroundClientMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long finishTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to proceed a request " + joinPoint.getSignature().getName() + " to GitHubRepositoryClient was:" +
                finishTime + " ms");
        return proceed;
    }
}
