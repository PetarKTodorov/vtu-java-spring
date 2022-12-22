package com.example.taxi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {

    @GetMapping("/login")
    public String loginGET(RedirectAttributes redirectAttributes) {
        return "pages/login";
//        try {
//            if (userService.isLogged()) throw new RuntimeException("User is already logged!");
//            return "login";
//        } catch (RuntimeException error) {
//            redirectAttributes
//                    .addFlashAttribute("error", error.getMessage());
//            return "redirect:/";
//        }
    }
}
