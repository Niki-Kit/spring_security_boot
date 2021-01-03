package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.service.PersonService;

@Controller
public class LoginController {

    @Autowired
    @Qualifier("personServiceImp")
    private PersonService personService;

    @GetMapping(value = "/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/{nameUser}")
    public String getUserPage( Model model, @PathVariable String nameUser) {
        model.addAttribute("person", personService.findByName(nameUser));
        return "user";
    }

    @GetMapping(value = "/adminPage")
    public String getAdminPage() {
        return "adminPage";
    }

}
