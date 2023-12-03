package ru.fedbon.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.fedbon.exception.NotFoundException;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView handleNotFoundException(Exception exception) {
        var modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", exception.getMessage());
        return modelAndView;
    }
}
