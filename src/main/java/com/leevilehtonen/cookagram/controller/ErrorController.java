package com.leevilehtonen.cookagram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String getError(Model model, HttpServletRequest request) {

        int httpErrorCode = getErrorCode(request);
        String errorMsg = null;
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Internal Server Error";
                break;
            }
        }
        model.addAttribute("header", "Error " + httpErrorCode);
        model.addAttribute("content", errorMsg);
        return "error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }

}
