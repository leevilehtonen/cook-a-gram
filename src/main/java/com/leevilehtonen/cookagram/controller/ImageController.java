package com.leevilehtonen.cookagram.controller;


import com.leevilehtonen.cookagram.domain.ImageEntity;
import com.leevilehtonen.cookagram.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String viewFiles(Model model) {
        List<ImageEntity> images = imageRepository.findAll();
        model.addAttribute("images", images);
        return "images";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        if (file == null || !file.getContentType().contains("image")) {
            redirectAttributes.addFlashAttribute("error", "File you submitted was not an image.");
            return "redirect:/images";
        }

        ImageEntity fo = new ImageEntity();
        fo.setName(file.getOriginalFilename());
        fo.setMediaType(file.getContentType());
        fo.setSize(file.getSize());
        fo.setContent(file.getBytes());
        imageRepository.save(fo);

        return "redirect:/images";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        ImageEntity fo = imageRepository.findOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getMediaType()));
        headers.setContentLength(fo.getSize());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }
}
