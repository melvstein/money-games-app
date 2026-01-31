package dev.melvstein.money_games.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        List<String> items = List.of("Item 1", "Item 2", "Item 3");
        model.addAttribute("items", items);
        return "index"; // resolves to templates/index.html
    }
}
