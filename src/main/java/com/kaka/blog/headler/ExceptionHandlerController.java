package com.kaka.blog.headler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kaka
 * 全局錯誤處理
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        /**
         * 處理404
         * @param clazz the class to look for annotations on
         * @param annotationType the type of annotation to look for
         * Returns: the first matching annotation, or null if not found
         * Spring-boot根據ResponseStatus找到對應的html
         */
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        logger.error("request Url : {}, Exception : {}", request.getRequestURL(), e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("request", request);
        mv.addObject("exception", e);
        System.out.println(e.getMessage());
        mv.setViewName("error/error");
        return mv;
    }
}