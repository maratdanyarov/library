package com.danyarov.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

/**
 * Global exception handler for the application
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TemplateInputException.class)
    public ModelAndView handleTemplateInputException(TemplateInputException e) {
        logger.error("Template error: ", e);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "Template not found: " + e.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e) {
        logger.error("Unexpected error: ", e);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "An unexpected error occurred: " + e.getMessage());
        return mav;
    }
}
