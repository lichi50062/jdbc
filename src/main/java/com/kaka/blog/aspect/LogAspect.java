package com.kaka.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Kaka
 */

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.kaka.blog.web.*.*(..))")
    private void pointcut() {
    }

    /**
     * 取得request的資訊
     * @param joinPoint
     */
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest httpServletRequest = attributes.getRequest();
        String ip = httpServletRequest.getRequestURI();
        String url = httpServletRequest.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        ReqInfo requestInfo = new ReqInfo(ip, url, className, args);

        logger.info("{}",requestInfo);
    }

    @After("pointcut()")
    public void doAfter() {
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void doAfterReturning(Object result) {
    }

    private static class ReqInfo {
        private final String url;
        private final String ip;
        private final String className;
        private final Object[] args;

        public ReqInfo(String url, String ip, String className, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.className = className;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReqInfo{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", meatheadName='" + className + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

