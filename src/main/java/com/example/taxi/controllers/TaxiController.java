package com.example.taxi.controllers;


import com.example.taxi.models.binding.SearchBM;
import com.example.taxi.models.binding.TaxiBM;
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
@RequestMapping("/taxi")
public class TaxiController {
    private final TaxiService taxiService;
    private final UserService userService;

    public TaxiController(TaxiService taxiService, UserService userService) {
        this.taxiService = taxiService;
        this.userService = userService;
    }

    @ModelAttribute
    public TaxiBM taxiBM() {
        return new TaxiBM();
    }

    @GetMapping("/create")
    public String addGET(RedirectAttributes redirectAttributes) {
        try {
            if (!userService.isLogged()) {
                throw new RuntimeException("User is not logged!");
            }

            return "pages/taxi/create";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());

            return "redirect:/";
        }
    }

    @PostMapping("/create")
    public ModelAndView addPOST(@Valid TaxiBM taxiBM, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                ModelAndView modelAndView = new ModelAndView("pages/taxi/create");

                modelAndView.addObject("taxiBM", taxiBM);

                return modelAndView;
            }

            taxiService.addTaxi(taxiBM, userService.findCurrentUser());

            ModelAndView modelAndView = new ModelAndView("pages/index");
            modelAndView.addObject("taxis", taxiService.findAllTaxisView());
            modelAndView.addObject("searchBM", new SearchBM());
            return modelAndView;
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("petBM", taxiBM);
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());

            ModelAndView modelAndView = new ModelAndView("pages/index");
            return modelAndView;
        }
    }

    @GetMapping("/edit/{id}")
    public String updateGET(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("taxiBM", taxiService.findTaxi(id));
            return "pages/taxi/edit";
        } catch (RuntimeException error) {
            redirectAttributes
                    .addFlashAttribute("error", error.getMessage());

            return "pages/index";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePOST(@Valid TaxiBM taxiBM, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("taxiBM", taxiBM);
                model.addAttribute("org.springframework.validation.BindingResult.taxi", bindingResult);

                return "pages/taxi/edit";
            }

            taxiService.updateTaxi(taxiBM, userService.findCurrentUser());

            return "redirect:/";
        } catch (RuntimeException error) {
            model.addAttribute("taxiBM", taxiBM);
            model.addAttribute("error", error.getMessage());

            return "pages/taxi/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGET(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taxiService.deleteTaxi(id, userService.findCurrentUser());
            return "redirect:/";
        } catch (RuntimeException error) {
            redirectAttributes.addFlashAttribute("error", error.getMessage());
            return "redirect:/";
        }
    }
}
