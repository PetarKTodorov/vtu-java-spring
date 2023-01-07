package com.example.taxi.controllers;

import com.example.taxi.models.binding.SearchBM;
import com.example.taxi.models.binding.UserRegisterBM;
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
public class RegisterController {
    private final UserService userService;
    private final TaxiService taxiService;

    public RegisterController(UserService userService, TaxiService taxiService) {
        this.taxiService = taxiService;
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterBM userRegisterBM() {
        return new UserRegisterBM();
    }

    @GetMapping("/register")
    public String registerGET(RedirectAttributes redirectAttributes) {
        try {
            if (userService.isLogged()) {
                throw new RuntimeException("User is already logged!");
            }

            return "pages/register";
        } catch (RuntimeException error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());

            return "redirect:/";
        }
    }

    @PostMapping("/register")
    public ModelAndView registerPOST(@Valid UserRegisterBM userRegisterBM, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !userRegisterBM.getPassword().equals(userRegisterBM.getConfirmPassword())) {
            ModelAndView modelAndView = new ModelAndView("pages/register");

            if (!userRegisterBM.getPassword().equals(userRegisterBM.getConfirmPassword())) {
                bindingResult.rejectValue("password", "error.userRegisterBM", "Password and Confirm password do not match.");
                bindingResult.rejectValue("confirmPassword", "error.userRegisterBM", "Password and Confirm password do not match.");
            }

            modelAndView.addObject("userRegisterBM", userRegisterBM);

            return modelAndView;
        }

        userService.registerUser(userRegisterBM);
        ModelAndView modelAndView = new ModelAndView("pages/index");
        modelAndView.addObject("taxis", taxiService.findAllTaxisView());
        modelAndView.addObject("searchBM", new SearchBM());

        return modelAndView;
    }

}
