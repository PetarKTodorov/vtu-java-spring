package com.example.taxi.controllers;

import com.example.taxi.models.binding.SearchBM;
import com.example.taxi.models.binding.UserLoginBM;
import com.example.taxi.services.TaxiService;
import com.example.taxi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {
    private final UserService userService;
    private final TaxiService taxiService;

    public LoginController(UserService userService, TaxiService taxiService) {
        this.taxiService = taxiService;
        this.userService = userService;
    }

    @ModelAttribute
    public UserLoginBM userLoginBM() {
        return new UserLoginBM();
    }

    @GetMapping("/login")
    public String loginGET(RedirectAttributes redirectAttributes) {
        try {
            if (userService.isLogged()) {
                throw new RuntimeException("User is already logged!");
            }
            return "pages/login";
        } catch (RuntimeException error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());

            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public ModelAndView loginPOST(@Valid UserLoginBM userLoginBM, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                ModelAndView modelAndView = new ModelAndView("pages/login");

                modelAndView.addObject("userLoginBM", userLoginBM);

                return modelAndView;
            }

            userService.loginUser(userLoginBM);
            ModelAndView modelAndView = new ModelAndView("pages/index");
            modelAndView.addObject("taxis", taxiService.findAllTaxisView());
            modelAndView.addObject("searchBM", new SearchBM());

            return modelAndView;
        } catch (RuntimeException error) {
            ModelAndView modelAndView = new ModelAndView("pages/login");

            modelAndView.addObject("userLoginBM", userLoginBM);
            bindingResult.rejectValue("email", "error.userLoginBM", "Email or password do not match.");
            bindingResult.rejectValue("password", "error.userLoginBM", "Email or password do not match.");

            return modelAndView;
        }
    }
}
