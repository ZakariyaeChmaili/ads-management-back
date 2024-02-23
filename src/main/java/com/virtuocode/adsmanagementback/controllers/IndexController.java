package com.virtuocode.adsmanagementback.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public ModelAndView index() {
        return new ModelAndView(new RedirectView("/swagger-ui.html"));
    }
}
