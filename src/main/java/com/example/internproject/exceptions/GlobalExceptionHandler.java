// error handling 
package com.example.internproject.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.file.AccessDeniedException;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", "Invalid input: " + ex.getMessage());
        return mav;
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ConfigDataResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", "Requested resource not found: " + ex.getMessage());
        return mav;
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ModelAndView handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", "OAuth authentication failed: " + ex.getError().getDescription());
        return mav;
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", "Access denied: " + ex.getMessage());
        return mav;
    }
    
    //it will handle all the exceptions
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex,HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage",  ex );

        return mav;
    }

   
}


