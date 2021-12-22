package com.example.ftp_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Version: V1.0    <br/>
 * Datetime:   2021/12/21 14:43   <br/>
 * Description: index
 *
 * @author: chen
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:index.html");
    }
}
