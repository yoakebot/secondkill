package com.secondkill.controller;

import com.secondkill.entity.SecKill;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class IndexController {

    @RequestMapping(value = "freemarker")
    public String freemarker(ModelMap map) {
        SecKill secKill = new SecKill();
        secKill.setName("freemarker");
        secKill.setStock(2);
        map.addAttribute("secKill",secKill);
        return "freemarker/index";
    }

    @RequestMapping(value = "thymeleaf")
    public String thymeleaf(ModelMap map) {
        SecKill secKill = new SecKill();
        secKill.setName("thymeleaf");
        secKill.setStock(2);
        secKill.setStartTime(new Date());
        map.addAttribute("secKill",secKill);
        return "thymeleaf/index";
    }
}
