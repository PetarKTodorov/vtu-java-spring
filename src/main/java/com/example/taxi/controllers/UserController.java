package com.example.taxi.controllers;

import com.example.taxi.models.binding.UserRegisterBM;
import com.example.taxi.models.entities.Taxi;
import com.example.taxi.models.entities.User;
import com.example.taxi.services.TaxiService;
import com.example.taxi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TaxiService taxiService;

    public UserController(UserService userService, TaxiService taxiService) {
        this.taxiService = taxiService;
        this.userService = userService;
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        try {
            userService.logout();

            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());

            return "redirect:/";
        }
    }

    @GetMapping("/like/{id}")
    public String updateGET(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Taxi taxi = taxiService.findById(id);
        User user = userService.findCurrentUser();

        userService.likeTaxi(user.getUsername(), taxi);

        return "redirect:/";
    }
}
