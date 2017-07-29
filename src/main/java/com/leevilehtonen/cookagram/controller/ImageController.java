package com.leevilehtonen.cookagram.controller;


import com.leevilehtonen.cookagram.domain.ImageEntity;
import com.leevilehtonen.cookagram.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller listening requests to images endpoint (adding an image is handled in postcontroller)
 *
 * @author lleevi
 * @see PostController
 */
@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Gets one image by id
     * @param id
     * @return response-entity containing image
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        ImageEntity fo = imageRepository.findOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getMediaType()));
        headers.setContentLength(fo.getSize());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }
}
