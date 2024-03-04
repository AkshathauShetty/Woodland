package com.woodland.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {
	Logger logger = LoggerFactory.getLogger(LogginAspect.class);

}
