package com.ubio.stockdemo.api.controller;

import com.ubio.stockdemo.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClassicController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/home")
//    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
//        if (principal != null) {
//            String name = principal.getAttribute("name");
//            String email = principal.getAttribute("email");
//            userService.saveOrUpdateUser(name, email);
//            model.addAttribute("name", name);
//            model.addAttribute("email", email);
//        }
//        return "home";
//    }
}
