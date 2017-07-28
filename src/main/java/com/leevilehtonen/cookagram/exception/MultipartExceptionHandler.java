package com.leevilehtonen.cookagram.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class MultipartExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String handleErrorOne(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "There was an error uploading an image (max size is 512 KB)");
        return "redirect:/post";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleErrorTwo(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "The image size is too large (max size 512 KB)");
        return "redirect:/post";
    }

    @ExceptionHandler(Exception.class)
    public String handleOther(Exception e, Model model) {
        model.addAttribute("header", "Error");
        model.addAttribute("content", "You seem to run some issues, get back!");
        return "error";
    }


}
