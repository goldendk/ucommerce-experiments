package org.ucommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("name", "foo");
        return "index";
    }

    @PostMapping("/modify-inventory")
    public String modifyInventory(String location, long quantity, String product, Model model) {
        model.addAttribute("name", "foo");
        return "redirect://";
    }

    @PostMapping("/set-inventory")
    public String setInventory(String location, long quantity, String product, Model model) {
        model.addAttribute("name", "foo");
        return "redirect://";
    }

}
