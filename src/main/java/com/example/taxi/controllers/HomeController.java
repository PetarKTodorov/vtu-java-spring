package com.example.taxi.controllers;

import com.example.taxi.models.binding.SearchBM;
import com.example.taxi.models.binding.TaxiBM;
import com.example.taxi.models.entities.User;
import com.example.taxi.services.TaxiService;
import com.example.taxi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;
    private final TaxiService taxiService;

    public HomeController(UserService userService, TaxiService taxiService) {
        this.taxiService = taxiService;
        this.userService = userService;
    }

    @ModelAttribute
    public SearchBM taxiBM() {
        return new SearchBM();
    }

    @GetMapping("/")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView("pages/index");

        modelAndView.addObject("taxis", taxiService.findAllTaxisView());
//        int a =  taxiService.findAllTaxisView().get(0).getLikedUsers().size();
//
//        modelAndView.addObject("likes", a);

        return modelAndView;
    }

    @PostMapping("/")
    public ModelAndView search(@Valid SearchBM searchBM, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            ModelAndView modelAndView = new ModelAndView("pages/index");

            if (bindingResult.hasErrors()) {

                modelAndView.addObject("searchBM", searchBM);

                return modelAndView;
            }

            modelAndView.addObject("taxis", taxiService.findSearchedTaxiView(searchBM.getCompanyName()));

            return modelAndView;
        } catch (RuntimeException error) {
            ModelAndView modelAndView = new ModelAndView("pages/index");

            modelAndView.addObject("error", error.getMessage());

            return modelAndView;
        }
    }
}
