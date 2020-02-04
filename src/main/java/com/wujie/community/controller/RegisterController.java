package com.wujie.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @RequestMapping("/toRegister/{accountId}")
    public String toRegister(@PathVariable("accountId") String accountId, Model model){
            model.addAttribute("accountId");
            return "register.html";
    }
}
