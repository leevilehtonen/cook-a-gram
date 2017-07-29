package com.leevilehtonen.cookagram.controller;


import com.leevilehtonen.cookagram.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller listening requests to relationship endpoint
 *
 * @author lleevi
 */
@Controller
@RequestMapping("/relationship")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    /**
     * Toggles the relationship from authenticated user to target user
     * @param target Account id
     * @return profile body
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String toggleRelationship(@RequestParam(name = "target") Long target) {
        relationshipService.toggleRelationship(target);
        return "profile";
    }

}
