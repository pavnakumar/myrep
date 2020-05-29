package com.development.myproject.creditCard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyCustomErrorController implements ErrorController {

  @RequestMapping("/error")
  @ResponseBody
  public String handleError(HttpServletRequest request) {
      Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
      return String.format("<html><body><h2>Error Page</h2>"
                      + "<div align='center' color=red>Message: <b>%s</b></div><body></html>",
               exception==null? "N/A": exception.getCause().getMessage());
  }

  @Override
  public String getErrorPath() {
      return "/error";
  }
}