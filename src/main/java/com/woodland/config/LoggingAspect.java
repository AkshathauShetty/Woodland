package com.woodland.config;

import java.util.Arrays;
import java.util.StringJoiner;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	 private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	/**
	 * Pointcut that matches all the service implementation methods with any number of arguments
	 * 
	 * loggingPointCut
	 */
	@Pointcut(
			"execution(* com.woodland.serviceImplementation.*.*(..))"+
			"|| execution(* com.woodland.controller.*.*(..))"+
			"|| execution(* com.woodland.repository.*.*(..))\""
			)
	public void applicationPackages() {	
	}
	
	 /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackages()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    	logger.error
    	("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    	logger.error(e.getMessage());
    }
	
	/**
     * Advice that logs when a method is entered and left.
     *
     * logAround
     * @param joinPoint join point for advice
     * @return Object
     * @exception Throwable 
     */
	@Around("applicationPackages()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		 long startTime = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),getArgsString(joinPoint.getArgs()));
//			getArgsString(joinPoint.getArgs())
//			Arrays.toString(joinPoint.getArgs())
        }
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
            	logger.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
                logger.info("Execution time: {} ms", endTime - startTime);
            }
            return result;
        } catch (IllegalArgumentException e) {
        	logger.error("Illegal argument: {} in {}.{}()",getArgsString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }		
	}

	private String getArgsString(Object[] args) {
		StringJoiner argsjoiner = new StringJoiner(",");
		for(Object arg : args) {
			argsjoiner.add(arg.toString());
		}
		return argsjoiner.toString();
	}
}
